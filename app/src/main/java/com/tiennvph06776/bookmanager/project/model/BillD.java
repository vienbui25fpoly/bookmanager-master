package com.tiennvph06776.bookmanager.project.model;

public class BillD {
    String ma,ngay;
    public BillD(String ma, String ngay) {
        this.ma = ma;
        this.ngay = ngay;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
