package edu.wgu.student.tomasgray.captstone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

abstract class Person
{
    protected UUID id; // will be overridden
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String phone;
//    private Address address;
//    private Image image;

    public UUID getId() {
        return id;
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

    // TODO: FIX THIS!!!!!
//    public Address getAddress() {
//        return address;
//    }
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//    public Image getImage() {
//        return image;
//    }
//    public void setImage(Image image) {
//        this.image = image;
//    }
}
