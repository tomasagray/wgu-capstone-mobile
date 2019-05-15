package edu.wgu.student.tomasgray.captstone.data.model;

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
    @SerializedName("student_id")
    private final UUID studentId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String phone;
//    private Address address;
//    private Image image;

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Ignore
    public Student(UUID id) {
        this.studentId = id;
    }
    @Ignore
    public Student(UUID id, String firstName, String lastName) {
        this(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
    // Used by Room
    public Student(UUID id, String firstName, String lastName, String email, String phone) {
        this(id, firstName, lastName);
        this.setEmail(email);
        this.setPhone(phone);
    }
}
