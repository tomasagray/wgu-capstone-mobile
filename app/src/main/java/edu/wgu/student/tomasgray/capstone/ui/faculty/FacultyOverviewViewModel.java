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

package edu.wgu.student.tomasgray.capstone.ui.faculty;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.data.access.FacultyRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

public class FacultyOverviewViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "FacultyOvVM";

    private LiveData<List<Faculty>> faculty;

    public FacultyOverviewViewModel(@NonNull Application application) {
        super(application);
    }

    void init(){
        faculty = FacultyRepository.getInstance(getApplication()).getAllFaculty();
    }

    LiveData<List<Faculty>> getFaculty() {
        return this.faculty;
    }
}
