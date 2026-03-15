import Foundation
import Shared

class NotesViewModelWrapper: ObservableObject {
    private let repository: NoteRepository
    @Published var notes: [Note] = []
    @Published var searchQuery: String = "" {
        didSet { observeNotes() }
    }

    private var collector: Closeable?

    init() {
        self.repository = KoinHelper.shared.getNoteRepository()
        observeNotes()
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
