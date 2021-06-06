package com.example.aquamanadmin;

public class UserInfo {
    private  String email;
    private  String fullName;
    private String homeAddress;
    private  String mobileNo;

    public UserInfo() {
    }

    public UserInfo(String email, String fullname, String homeaddress, String mobileNo) {
        this.email = email;
        this.fullName = fullname;
        this.homeAddress = homeaddress;
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullName;
    }

    public String getHomeaddress() {
        return homeAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeAddress = homeaddress;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
