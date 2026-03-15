import SwiftUI
import Shared

struct ContentView: View {
    @StateObject private var viewModel = NotesViewModelWrapper()

    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    Image(systemName: "magnifyingglass")
                        .foregroundColor(.secondary)
                    TextField("Search notes...", text: $viewModel.searchQuery)
                }
                .padding(10)
                .background(Color(.systemGray6))
                .cornerRadius(12)
                .padding(.horizontal)

                if viewModel.isLoading && viewModel.notes.isEmpty {
                    Spacer()
                    ProgressView("Loading...")
                    Spacer()
                } else if viewModel.notes.isEmpty {
                    Spacer()
                    Text(viewModel.searchQuery.isEmpty
                         ? "No notes yet.\nTap + to create one."
                         : "No notes found.")
                        .foregroundColor(.secondary)
                        .multilineTextAlignment(.center)
                    Spacer()
                } else {
                    List {
                        ForEach(viewModel.notes, id: \.id) { note in
                            NavigationLink(value: note.id) {
                                NoteRow(
                                    note: note,
                                    onFavorite: { viewModel.toggleFavorite(note: note) },
                                    onDelete: { viewModel.deleteNote(id: note.id) }
                                )
                            }
                        }
                    }
                    .listStyle(.plain)
                    .refreshable {
                        viewModel.sync()
                    }
                }
            }
            .navigationTitle("My Notes")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button(action: { viewModel.showNewNote = true }) {
                        Image(systemName: "plus")
                    }
                }
            }
            .navigationDestination(for: Int64.self) { noteId in
                NoteEditView(
                    noteId: noteId,
                    repository: viewModel.repository,
                    onSave: { viewModel.refresh() }
                )
            }
            .sheet(isPresented: $viewModel.showNewNote) {
                NavigationStack {
                    NoteEditView(
                        noteId: nil,
                        repository: viewModel.repository,
                        onSave: { viewModel.refresh() }
                    )
                }
            }
            .alert("Sync Error", isPresented: $viewModel.showSyncError) {
                Button("OK", role: .cancel) {}
            } message: {
                Text(viewModel.syncErrorMessage)
            }
        }
    }
}

struct NoteRow: View {
    let note: Note
    let onFavorite: () -> Void
    let onDelete: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Circle()
                    .fill(colorForNote(note.color))
                    .frame(width: 12, height: 12)
                Text(note.title)
                    .font(.headline)
                    .lineLimit(1)
                Spacer()
                Button(action: onFavorite) {
                    Image(systemName: note.isFavorite ? "heart.fill" : "heart")
                        .foregroundColor(note.isFavorite ? .red : .secondary)
                }
                .buttonStyle(.plain)
                Button(action: onDelete) {
                    Image(systemName: "trash")
                        .foregroundColor(.red)
                }
                .buttonStyle(.plain)
            }
            Text(note.content)
                .font(.subheadline)
                .foregroundColor(.secondary)
                .lineLimit(3)
        }
        .padding(.vertical, 4)
    }
}

func colorForNote(_ noteColor: NoteColor) -> Color {
    switch noteColor {
    case .red: return .red
    case .orange: return .orange
    case .yellow: return .yellow
    case .green: return .green
    case .blue: return .blue
    case .purple: return .purple
    default: return Color(.systemGray4)
    }
}
