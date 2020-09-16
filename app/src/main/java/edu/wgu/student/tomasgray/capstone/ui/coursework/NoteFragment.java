/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.R;

public class NoteFragment extends Fragment
{
    private static final String LOG_TAG = "NoteFragment";

    // To identify the Note for this Fragment
    public static final String ARG_NOTE_ID = "note_id";


    // Data
    // --------------------------------------------------------
    private NoteViewModel viewModel;
    private UUID noteId;

    // GUI
    // --------------------------------------------------------
    private ConstraintLayout noteBody;
    private TextView noteDate;
    private TextView noteText;
    private OnNoteInteractionListener callback;


    public static NoteFragment newInstance(@NonNull UUID noteId)
    {
        // Assemble Fragment
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOTE_ID, noteId.toString());
        fragment.setArguments(args);

        return fragment;
    }

    public NoteFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            noteId = UUID.fromString( getArguments().getString(ARG_NOTE_ID) );
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return
                inflater
                        .inflate(
                                R.layout.note_fragment,
                                container,
                                false
                        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Initialize GUI
        initializeGui();
        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        // TODO: Use the ViewModel

        if(noteId != null) {
            viewModel.init(noteId);
            viewModel.getNote().observe(this, note -> {
                if(note != null) {
                    // Format data
                    String date = note
                            .getUpdateDate()
                            .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
                    // Update GUI
                    noteDate.setText(date);
                    noteText.setText(note.getNoteText());
                }
            });
        }
    }

    private void initializeGui()
    {
        // Get GUI references
        noteBody = getView().findViewById(R.id.noteBody);
        noteBody.setOnClickListener(v -> callback.onNoteClick());
        noteDate = getView().findViewById(R.id.noteDateText);
        noteText = getView().findViewById(R.id.noteText);
    }


    public interface OnNoteInteractionListener {
        void onNoteClick();
    }
    public void setNoteInteractionListener(OnNoteInteractionListener callback) {
        this.callback = callback;
    }
}
