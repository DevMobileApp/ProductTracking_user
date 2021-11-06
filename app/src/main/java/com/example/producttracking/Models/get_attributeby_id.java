package com.example.producttracking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class get_attributeby_id
{

    @SerializedName("_id")
    @Expose
    public String ID;

    @SerializedName("roleId")
    @Expose
    public String roleType;

    ArrayList < Object > attributes = new ArrayList< Object >();
    private String _id;
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
}
