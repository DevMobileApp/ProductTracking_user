package com.example.producttracking.Models;

public class Model {
    private String id;
    private String company_name;
    private String location_transfer;
    private String describe;
    private String time;
    private String date;
//    private String phone;
    private byte[] image;

    public Model(String id,String company_name,String location_transfer, String describe, String date,String time ) {
        this.id = id;
        this.company_name = company_name;
        this.location_transfer = location_transfer;
        this.describe = describe;
        this.time = time;
        this.date = date;
//        this.phone = phone;
//        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getcompany_name() {
        return company_name;
    }

    public void setcompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getlocation_transfer() {
        return location_transfer;
    }

    public void setlocation_transfer(String location_transfer) {
        this.location_transfer = location_transfer;
    }

    public String getdescribe() {
        return describe;
    }

    public void setdescribee(String describe) {
        this.describe = describe;
    }


//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
