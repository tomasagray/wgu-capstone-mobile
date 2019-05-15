package edu.wgu.student.tomasgray.captstone.data.access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Address;

@Dao
abstract class AddressDao implements BaseDao<Address>
{
    @Query("SELECT * FROM address WHERE addressId = :addressId")
    abstract LiveData<Address> load(UUID addressId);
}
