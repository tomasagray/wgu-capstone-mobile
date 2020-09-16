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

package edu.wgu.student.tomasgray.capstone.ui.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.data.model.Note;
import edu.wgu.student.tomasgray.capstone.ui.coursework.NoteFragment;

// Notes ViewPager
public class NoteSlidePagerAdapter extends FragmentStatePagerAdapter
    implements NoteFragment.OnNoteInteractionListener
{
    // TODO: Rearrange this class
    private static final String LOG_TAG = "NoteSlideAdapter";

    // Data
    private List<Note> noteList;

    public NoteSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private NoteSlidePagerAdapter.OnSliderClickListener callback;

    @Override
    public void onNoteClick() {
        callback.onSlideClick();
    }

    public interface OnSliderClickListener {
        void onSlideClick();
    }
    public void setOnSliderClickListener(NoteSlidePagerAdapter.OnSliderClickListener callback) {
        this.callback = callback;
    }

    public void setData(@NonNull List<Note> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        NoteFragment noteFragment
                = NoteFragment
                    .newInstance(
                            // Get Note from list
                            noteList.get(position).getNoteId()
                    );
        noteFragment.setNoteInteractionListener(this);

        return noteFragment;
    }

    @Override
    public int getCount() {
        return (noteList == null) ? 0 : noteList.size();
    }
}