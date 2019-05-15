package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

@Entity
public class Address
{
    @PrimaryKey
    @NonNull
    @SerializedName("address_id")
    private final UUID addressId;
    @SerializedName("building_number")
    private String buildingNumber;
    private String street;
    @SerializedName("unit_number")
    private String unitNumber;
    private String city;
    private String state;
    private String country;
    @SerializedName("post_code")
    private String postCode;


    // Constructors
    // -------------------------------------------------------------
    @Ignore
    public Address(UUID addressId) {
        this.addressId = addressId;
    }
    // Used by Room
    public Address(UUID addressId, String buildingNumber, String street, String unitNumber, String city,
                    String state, String country, String postCode)
    {
        this(addressId);

        this.buildingNumber = buildingNumber;
        this.street = street;
        this.unitNumber = unitNumber;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postCode = postCode;
    }

    // Getters & Setters
    // -------------------------------------------------------------
    @NonNull
    public UUID getAddressId() {
        return addressId;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return
                "ID: " + addressId + "\n"
                +"Address: " + buildingNumber + ' ' + street
                + ' ' + unitNumber + ' ' + city + ' ' + state
                +' ' + country + ' ' + postCode;
    }
}
