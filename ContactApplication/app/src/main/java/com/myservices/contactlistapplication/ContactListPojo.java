package com.myservices.contactlistapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactListPojo implements Serializable {

    public String name;
    public String phoneNumber;
    public String emailId;


    public void setName(String name){
        this.name=name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public void setEmailId(String emailId){
        this.emailId=emailId;
    }


    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getEmailId(){
        return emailId;
    }



}
