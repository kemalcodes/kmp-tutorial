import Foundation
import Shared

class NotesViewModelWrapper: ObservableObject {
    let repository: NoteRepository
    @Published var notes: [Note] = []
    @Published var searchQuery: String = "" {
        didSet { observeNotes() }
    }
    @Published var isLoading: Bool = false
    @Published var showNewNote: Bool = false
    @Published var showSyncError: Bool = false
    var syncErrorMessage: String = ""

    private var collector: Closeable?

    init() {
        self.repository = KoinHelper.shared.getNoteRepository()
        observeNotes()
        sync()
    }

    private func observeNotes() {
        collector?.close()
        let flow = searchQuery.isEmpty
            ? repository.getAllNotes()
            : repository.searchNotes(query: searchQuery)

        collector = FlowCollectorCompanion.shared.collect(flow: flow) { [weak self] value in
            let notesList = value as? [Note] ?? []
            DispatchQueue.main.async {
                self?.notes = notesList
            }
        }
    }

    func refresh() {
        observeNotes()
    }

    func sync() {
        isLoading = true
        Task {
            let result = try await repository.sync()
            DispatchQueue.main.async { [weak self] in
                self?.isLoading = false
                if let error = result as? SyncResult.Error {
                    self?.syncErrorMessage = error.message
                    self?.showSyncError = true
                }
            }
        }
    }

    func createNote() {
        _ = repository.createNote(title: "New Note", content: "Tap to edit...", color: .default_)
        observeNotes()
    }

    func toggleFavorite(note: Note) {
        repository.updateNote(
            note: note.doCopy(
                id: note.id,
                title: note.title,
                content: note.content,
                createdAt: note.createdAt,
                updatedAt: note.updatedAt,
                isFavorite: !note.isFavorite,
                color: note.color
            )
        )
    }

    func deleteNote(id: Int64) {
        repository.deleteNote(id: id)
    }

    deinit {
        collector?.close()
    }
}
