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

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Address;

// TODO: DELETE this class?
@Dao
public abstract class AddressDao implements BaseDao<Address>
{
    @Query("SELECT * FROM address WHERE addressId = :addressId")
    abstract LiveData<Address> load(UUID addressId);

    @Query("DELETE FROM address")
    abstract void deleteAll();
}
