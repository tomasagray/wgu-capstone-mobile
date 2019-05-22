package edu.wgu.student.tomasgray.captstone.ui.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import edu.wgu.student.tomasgray.captstone.data.model.Note;
import edu.wgu.student.tomasgray.captstone.ui.coursework.NoteFragment;

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