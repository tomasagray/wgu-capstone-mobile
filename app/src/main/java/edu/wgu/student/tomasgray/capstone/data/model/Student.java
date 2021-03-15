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
public class Student
{
    @PrimaryKey
    @NonNull
    @SerializedName("user_id")
    private final UUID studentId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String phone;

    // Address
    @SerializedName("building_number")
    private String buildingNumber;
    private String street;
    @SerializedName("unit_number")
    private String unitNumber;
    private String city;
    private String state;
    @SerializedName("post_code")
    private String zip;
    @SerializedName("image_uri")
    private String imgUri;

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

    public String getAddress() {
        StringBuilder sb = new StringBuilder();
        sb
            .append(getBuildingNumber())
            .append(" ")
            .append(getStreet());

        if(getUnitNumber() != null) {
            sb.append(" ")
                .append(getUnitNumber());
        }

        return sb.toString();
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
    public String getImgUri() {
        return imgUri;
    }
    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @NonNull
    public UUID getStudentId() {
        return this.studentId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public String getPhoneFormatted() {
        if(phone == null)
            return "";

        // Format phone number String

        return "(" +
                phone.substring(0, 3) +
                ") " +
                phone.substring(3, 6) +
                "-" +
                phone.substring(6, 10);
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Ignore
    public Student(@NonNull UUID id) {
        this.studentId = id;
    }
    @Ignore
    public Student(UUID id, String firstName, String lastName) {
        this(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
    // Used by Room
    public Student(UUID studentId, String firstName, String lastName, String email, String phone) {
        this(studentId, firstName, lastName);
        this.setEmail(email);
        this.setPhone(phone);
    }

    @NonNull
    public String toString() {
        return
                "ID: " + this.getStudentId() + ", Name: "+ getFullName()
                +"\nEmail: " + this.getEmail() + ", phone: " + getPhone();
    }
}
