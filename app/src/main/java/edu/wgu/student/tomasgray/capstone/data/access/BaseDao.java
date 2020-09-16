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

import androidx.room.Insert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static androidx.room.OnConflictStrategy.REPLACE;


public interface BaseDao<T>
{
    // Refresh rate, to prevent repeated pings of remote DB
    Duration refreshRate = Duration.of(5, ChronoUnit.SECONDS);

    @Insert(onConflict = REPLACE)
    void save(T data);
}
