package com.example.producttracking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterModel implements Serializable
{
    @SerializedName("roleId")
    @Expose
    public String roleId;

    @SerializedName("firstName")
    @Expose
    public String firstName;

    @SerializedName("lastName")
    @Expose
    public String lastName;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("location")
    @Expose
    public String location;

    @SerializedName("attributeId")
    @Expose
    public String attributeId;
}
