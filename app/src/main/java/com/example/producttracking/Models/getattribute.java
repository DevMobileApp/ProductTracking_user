package com.example.producttracking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class getattribute {

    ArrayList <attributes> attributes = new ArrayList < attributes > ();

    @SerializedName("_id")
    private String _id;

    @SerializedName("roleId")
    private String roleId;


    // Getter Methods

    public String get_id() {
        return _id;
    }

    public String getRoleId() {
        return roleId;
    }

    // Setter Methods

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    public  List<attributes> getattributes() {
        return attributes;
    }

    public void setattributes( ArrayList<attributes> attributes) {
        this.attributes = attributes;
    }

}

