package com.example.producttracking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rollid_model implements Serializable {
    @SerializedName("_id")
    @Expose
    public String ID;

    @SerializedName("roleType")
    @Expose
    public String roleType;

    @SerializedName("createdAt")
    @Expose
    public String createdAt;

    @SerializedName("isAdmin")
    @Expose
    public boolean isAdmin;

    @SerializedName("isDatawriter")
    @Expose
    public boolean isDatawriter;

    public void deptdetails(String ID, String roleType, String createdAt, boolean isAdmin , boolean isDatawriter) {

        this.ID = ID;

        this.roleType = roleType;

        this.createdAt = createdAt;

        this.isAdmin = isAdmin;

        this.isDatawriter = isDatawriter;
    }



    // ID
    public String getID() { return ID; }

    public void setID(String ID)
    {
        this.ID = ID;
    }


    //Deptname

    public String getCategoryName()
    {
        return roleType;
    }

    public void setCategoryName(String roleType)
    {
        this.roleType = roleType;
    }

    //Deptname

    public String getProjectCategoryName()
    {
        return createdAt;
    }

    public void setProjectCategoryName (String createdAt)  {  this.createdAt = createdAt;    }


    //isActive

    public boolean getActive()
    {
        return isAdmin;
    }

    public void setActive(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }


    // ID
    public boolean getisDatawriter() { return isDatawriter; }

    public void setisDatawriter(boolean isDatawriter)
    {
        this.isDatawriter = isDatawriter;
    }

    @Override
    public String toString() {
        return roleType;
    }
}
