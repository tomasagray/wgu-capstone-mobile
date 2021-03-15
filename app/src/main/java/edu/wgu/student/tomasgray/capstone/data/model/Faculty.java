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
public class Faculty
{
    @PrimaryKey
    @NonNull
    @SerializedName("user_id")
    private final UUID facultyId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String phone;
//    private Address address;
//    private Image image;

    @NonNull
    public UUID getFacultyId() {
        return facultyId;
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
    public Faculty(@NonNull UUID facultyId) {
        this.facultyId = facultyId;
    }
    @Ignore
    public Faculty(UUID facultyId, String firstName, String lastName) {
        this(facultyId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
    // Used by Room
    public Faculty(UUID facultyId, String firstName, String lastName, String email, String phone) {
        this(facultyId, firstName, lastName);
        this.setEmail(email);
        this.setPhone(phone);
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Faculty ID: " + facultyId + "\n" +
                        "Name: " + firstName + " " + lastName + "\n" +
                        "Phone: " + this.getPhoneFormatted() + "\n" +
                        "Email: " + email;
    }

}
