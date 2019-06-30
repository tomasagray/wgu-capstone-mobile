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

        StringBuilder sb = new StringBuilder();
        // Format phone number String
        sb
            .append("(")
            .append(phone.substring(0,3))
            .append(") ")
            .append(phone.substring(3,6))
            .append("-")
            .append(phone.substring(6,10));

        return sb.toString();
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
