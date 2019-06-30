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
