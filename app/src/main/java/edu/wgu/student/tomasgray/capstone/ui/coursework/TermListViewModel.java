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

import java.util.List;

import edu.wgu.student.tomasgray.capstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Term;


public class TermListViewModel extends AndroidViewModel
{
    private LiveData<List<Term>> termList;

    public TermListViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Initializes the data repository for this View, and
     * returns the number of terms returned from the repo
     *
     * @param termRepository
     * @return Number of Terms retrieved
     */
    void init(TermRepository termRepository) {
        this.termList = termRepository.getTerms();
    }

    LiveData<List<Term>> getTermList() {
        return termList;
    }
}
