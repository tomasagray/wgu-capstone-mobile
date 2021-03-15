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

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.capstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Term;

// TODO: DELETE THIS CLASS
public class OverviewViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "OverviewVM";


    private final TermRepository termRepo;
    // LiveData
//    private LiveData<List<Course>> courses;
    private LiveData<Term> term;

    public OverviewViewModel(@NonNull Application application) {
        super(application);
        // Save repo reference
        // Repos
        CourseRepository courseRepo = CourseRepository.getInstance(getApplication());
        this.termRepo = TermRepository.getInstance(getApplication());
    }

    void init(UUID termId) {
        // Get Term LiveData
        term = termRepo.getTerm(termId);
    }

    public LiveData<Term> getTerm() {
        return term;
    }

}
