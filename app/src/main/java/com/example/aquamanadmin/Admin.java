package com.example.aquamanadmin;

import android.widget.EditText;

public class Admin {
    private String fullName,homeAddress,mobileNo,email,password;
    public Admin()
    {

    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin(String fullName, String homeAddress, String mobileNo, String email) {
        this.fullName = fullName;
        this.homeAddress = homeAddress;
        this.mobileNo = mobileNo;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

