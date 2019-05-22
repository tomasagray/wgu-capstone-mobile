package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import java.time.LocalDate;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.access.NoteRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Note;

import static edu.wgu.student.tomasgray.captstone.ui.coursework.NoteFragment.ARG_NOTE_ID;

public class NoteEditorActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "NoteEditActivity";

    // Edit modes
    private static final int NEW_NOTE_MODE = 100;
    private static final int EDIT_NOTE_MODE = 101;

    // ViewModel
    private NoteViewModel viewModel;
    // Note represented by this Activity
    private Note note;
    private int editMode;

    // GUI
    private Button cancelButton;
    private Button saveButton;
    private EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Attach GUI references
        initializeGui();

        // Unpack extras
        Bundle extras = getIntent().getExtras();

        // Edit existing note
        if(extras != null) {
            // Title
            setTitle(getResources().getString(R.string.edit_note));
            // Mode
            this.editMode = EDIT_NOTE_MODE;
            // Initialize GUI
            UUID note_id = (UUID)extras.get(ARG_NOTE_ID);
            Log.i(LOG_TAG, "We are editing note: " + note_id);
            initializeViewModel( note_id );
        // Create new note
        } else {
            // Title
            Log.i(LOG_TAG, "We are creating a new note");
            setTitle(getResources().getString(R.string.new_note));
            // Mode
            this.editMode = NEW_NOTE_MODE;
        }
    }

    @Override
    public void onBackPressed()
    {
        Log.i(LOG_TAG, "Back pressed");
        cancelEdit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelEdit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeGui()
    {
        cancelButton = findViewById(R.id.cancelNoteButton);
        saveButton = findViewById(R.id.saveNoteButton);
        noteText = findViewById(R.id.noteText);

        // Button handlers
        cancelButton.setOnClickListener(b -> cancelEdit());
        saveButton.setOnClickListener(b -> saveEdit());
    }


    private void initializeViewModel(UUID noteId)
    {
        // Initialize VM
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        viewModel.init(noteId);
        viewModel.getNote()
                .observe(this, note -> {
                    // Update local note
                    this.note = note;
                    // Update GUI
                    noteText.setText(note.getNoteText());
                });
    }


    private void cancelEdit()
    {
        // TODO: Handle cancel action
        Log.i(LOG_TAG, "User canceled edit");
        // TODO: Check if we should go back
        setResult(RESULT_CANCELED);
        finish();
    }

    private void saveEdit()
    {
        // Test for empty text
        if("".equals(noteText.getText().toString())) {
            Toast.makeText(
                    this,
                    "Note is currently blank. Please enter some text.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        if(editMode == NEW_NOTE_MODE) {
            // Create new note with random ID
            Note savedNote = new Note( UUID.randomUUID() );
            savedNote.setNoteText(noteText.getText().toString());
            savedNote.setUpdateDate( LocalDate.now() );
            // Save new note
            NoteRepository
                    .getInstance(getApplicationContext())
                    .saveNote(savedNote);
            // Set result for calling Activity
            setResult(RESULT_OK);
        } else {
            // EDIT_NOTE_MODE
            Note savedNote = new Note(this.note.getNoteId());
            savedNote.setNoteText(noteText.getText().toString());
            savedNote.setUpdateDate(LocalDate.now());
            // Update note
            NoteRepository
                    .getInstance(getApplicationContext())
                    .updateNote(savedNote);

            // Return to previous Activity
            setResult(RESULT_OK);
        }

        finish();
    }
}
