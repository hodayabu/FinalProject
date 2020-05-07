package com.example.finalproject.ResponseObjects;

public class mailMsg {

    String msg;
    String partner;

    public mailMsg(String msg,String partner) {
        this.msg = msg;
        this.partner=partner;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
