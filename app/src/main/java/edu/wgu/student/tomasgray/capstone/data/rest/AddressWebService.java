package edu.wgu.student.tomasgray.capstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Address;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AddressWebService
{
    @GET("addresses")
    Call<List<Address>> getAllAddresses();

    @GET("addresses/{address_id}")
    Call<Address> getAddress(@Path("address_id") UUID addressId );
}
