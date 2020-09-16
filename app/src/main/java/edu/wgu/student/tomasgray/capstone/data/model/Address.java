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

package edu.wgu.student.tomasgray.capstone.data.model;

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
    @SerializedName("post_code")
    private String postCode;


    // Constructors
    // -------------------------------------------------------------
    @Ignore
    public Address(@NonNull UUID addressId) {
        this.addressId = addressId;
    }
    // Used by Room
    public Address(UUID addressId, String buildingNumber, String street, String unitNumber, String city,
                    String state, String postCode)
    {
        this(addressId);

        this.buildingNumber = buildingNumber;
        this.street = street;
        this.unitNumber = unitNumber;
        this.city = city;
        this.state = state;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "ID: " + addressId + "\n"
                +"Address: " + buildingNumber + ' ' + street
                + ' ' + unitNumber + ' ' + city + ' ' + state
                + ' ' + postCode;
    }
}
