package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.data.rest.AddressWebService;
import edu.wgu.student.tomasgray.captstone.data.rest.RestClient;

public class AddressRepository
{
    private static final String LOG_TAG = "AddressRepo";

    // Singleton
    // -----------------------------------------------------
    private static volatile AddressRepository INSTANCE;
    public static AddressRepository getInstance(Context context) {
        if(INSTANCE == null) {
            AddressDao addressDao
                    = AddressDatabase
                        .getInstance(context)
                        .addressDao();
            AddressWebService webService
                    = RestClient
                        .getInstance()
                        .create(AddressWebService.class);
            ExecutorService executor
                    = Executors.newCachedThreadPool();

            INSTANCE = new AddressRepository(addressDao, webService, executor);
        }

        return INSTANCE;
    }

    // Fields
    // --------------------------------------------------
    private AddressDao addressDao;
    private AddressWebService webService;
    private Executor executor;

    // Constructor
    private AddressRepository(AddressDao dao, AddressWebService webService, Executor executor) {
        this.addressDao = dao;
        this.webService = webService;
        this.executor = executor;
    }
}
