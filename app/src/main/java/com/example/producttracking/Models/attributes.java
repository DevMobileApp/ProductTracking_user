package com.example.producttracking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class attributes {

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("placeholder")
    @Expose
    private String  placeholder;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("key")
    @Expose
    private String key;

//    public attributes(String label, String description, String placeholder, String type) {
//        this.label = label;
//        this.description = description;
//        this.placeholder = placeholder;
//        this.type = type;
////        this.image = image;
//    }

    public String getlabel() {
        return label;
    }
    public void setlabel(String label) {
        this.label = label;
    }

    public String getdescription() {
        return description;
    }
    public void setdescription(String description) {
        this.description = description;
    }

    public String getplaceholder() {
        return placeholder;
    }
    public void setplaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String gettype() {
        return type;
    }
    public void settype(String type) {
        this.type = type;
    }

    public String getkey() {
        return key;
    }
    public void setkey(String key) {
        this.key = key;
    }
}
