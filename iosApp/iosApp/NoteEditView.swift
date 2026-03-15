import SwiftUI
import Shared

struct NoteEditView: View {
    let noteId: Int64?
    let repository: NoteRepository
    let onSave: () -> Void

    @Environment(\.dismiss) private var dismiss

    @State private var title: String = ""
    @State private var content: String = ""
    @State private var selectedColor: NoteColor = .default_

    private var collector: Closeable?

    init(noteId: Int64?, repository: NoteRepository, onSave: @escaping () -> Void) {
        self.noteId = noteId
        self.repository = repository
        self.onSave = onSave
    }

    var body: some View {
        VStack(spacing: 16) {
            TextField("Title", text: $title)
                .font(.title2)
                .padding(.horizontal)

            TextEditor(text: $content)
                .padding(.horizontal, 12)
                .overlay(
                    Group {
                        if content.isEmpty {
                            Text("Write your note...")
                                .foregroundColor(.secondary)
                                .padding(.horizontal, 16)
                                .padding(.top, 8)
                        }
                    },
                    alignment: .topLeading
                )

            VStack(alignment: .leading, spacing: 8) {
                Text("Color")
                    .font(.subheadline)
                    .fontWeight(.medium)
                HStack(spacing: 12) {
                    ForEach(noteColors, id: \.self) { noteColor in
                        Circle()
                            .fill(colorForNote(noteColor))
                            .frame(width: 36, height: 36)
                            .overlay(
                                Circle()
                                    .stroke(Color.primary, lineWidth: selectedColor == noteColor ? 3 : 0)
                            )
                            .onTapGesture {
                                selectedColor = noteColor
                            }
                    }
                }
            }
            .padding(.horizontal)
        }
        .padding(.top)
        .navigationTitle(noteId != nil ? "Edit Note" : "New Note")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button("Save") {
                    saveNote()
                    onSave()
                    dismiss()
                }
                .disabled(title.trimmingCharacters(in: .whitespaces).isEmpty)
            }
            ToolbarItem(placement: .cancellationAction) {
                if noteId == nil {
                    Button("Cancel") {
                        dismiss()
                    }
                }
            }
        }
        .onAppear {
            loadNote()
        }
    }

    private func loadNote() {
        guard let noteId = noteId else { return }
        let flow = repository.getNoteById(id: noteId)
        _ = FlowCollectorCompanion.shared.collect(flow: flow) { [self] value in
            if let note = value as? Note {
                DispatchQueue.main.async {
                    self.title = note.title
                    self.content = note.content
                    self.selectedColor = note.color
                }
            }
        }
    }

    private func saveNote() {
        if let noteId = noteId {
            let now = Kotlinx_datetimeInstant.companion.fromEpochMilliseconds(
                epochMilliseconds: Int64(Date().timeIntervalSince1970 * 1000)
            )
            // For updates, load existing note and update it
            repository.updateNote(
                note: Note(
                    id: noteId,
                    title: title,
                    content: content,
                    createdAt: now,
                    updatedAt: now,
                    isFavorite: false,
                    color: selectedColor
                )
            )
        } else {
            _ = repository.createNote(
                title: title,
                content: content,
                color: selectedColor
            )
        }
    }
}

private let noteColors: [NoteColor] = [
    .default_, .red, .orange, .yellow, .green, .blue, .purple
]
