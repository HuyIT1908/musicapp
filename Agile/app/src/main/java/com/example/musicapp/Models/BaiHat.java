package com.example.musicapp.Models;

public class BaiHat {
    int idbaihat;
    String tencasi;
    String trangthai;
    int hinhanhbaihat;

    public int getIdbaihat() {
        return idbaihat;
    }

    public void setIdbaihat(int idbaihat) {
        this.idbaihat = idbaihat;
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

    public BaiHat(int idbaihat, String tencasi, String trangthai, int hinhanhbaihat) {
        this.idbaihat = idbaihat;
        this.tencasi = tencasi;
        this.trangthai = trangthai;
        this.hinhanhbaihat = hinhanhbaihat;
    }
}
