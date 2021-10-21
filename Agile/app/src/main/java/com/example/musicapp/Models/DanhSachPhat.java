package com.example.musicapp.Models;

public class DanhSachPhat {
    int iddanhsachPhat;
    String tendanhsachPhat;

    public DanhSachPhat(int iddanhsachPhat, String tendanhsachPhat) {
        this.iddanhsachPhat = iddanhsachPhat;
        this.tendanhsachPhat = tendanhsachPhat;
    }

    public DanhSachPhat() {
    }

    public int getIddanhsachPhat() {
        return iddanhsachPhat;
    }

    public void setIddanhsachPhat(int iddanhsachPhat) {
        this.iddanhsachPhat = iddanhsachPhat;
    }

    public String getTendanhsachPhat() {
        return tendanhsachPhat;
    }

    public void setTendanhsachPhat(String tendanhsachPhat) {
        this.tendanhsachPhat = tendanhsachPhat;
}
}
