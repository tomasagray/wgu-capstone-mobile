package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.capstone.data.model.Address;
import edu.wgu.student.tomasgray.capstone.data.model.Converters;

// TODO: Delete this class
@Database( entities = {Address.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AddressDatabase extends RoomDatabase
{
    // DAO
    public abstract AddressDao addressDao();

    // Singleton
    // --------------------------------------------------------
    private static volatile AddressDatabase INSTANCE;
    static AddressDatabase getInstance(Context context)
    {
        if(INSTANCE == null) {
            synchronized (AddressDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE
                            = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AddressDatabase.class,
                                    "address_database"
                            ).build();
                }
            }
        }

        return INSTANCE;
    }
}
