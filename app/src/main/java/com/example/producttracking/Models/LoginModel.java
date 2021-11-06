package com.example.producttracking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {

    @SerializedName("email")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;
}
