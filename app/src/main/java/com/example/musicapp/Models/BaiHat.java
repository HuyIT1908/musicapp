package com.example.musicapp.Models;

public class BaiHat {
    int idbaihat;
    String tenBaiHat;
    String tencasi;
    String trangthai;
    int hinhanhbaihat;

    public BaiHat() {
    }

    public BaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public int getIdbaihat() {
        return idbaihat;
    }

    public void setIdbaihat(int idbaihat) {
        this.idbaihat = idbaihat;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getTencasi() {
        return tencasi;
    }

    public void setTencasi(String tencasi) {
        this.tencasi = tencasi;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getHinhanhbaihat() {
        return hinhanhbaihat;
    }

    public void setHinhanhbaihat(int hinhanhbaihat) {
        this.hinhanhbaihat = hinhanhbaihat;
    }
}
