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

package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.capstone.data.model.Converters;
import edu.wgu.student.tomasgray.capstone.data.model.Note;

// TODO: DELETE THIS
@Database( entities = {Note.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase
{
    // DAO
    public abstract NoteDao noteDao();

    // Singleton
    // ----------------------------------------------------------
    private static volatile NoteDatabase INSTANCE;
    static NoteDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE
                            = Room
                                .databaseBuilder(
                                        context,
                                        NoteDatabase.class,
                                        "note_database"
                                ).build();
                }
            }
        }
        return INSTANCE;
    }
}
