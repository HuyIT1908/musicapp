package com.example.musicapp.Models;

public class DanhSachChiTiet {
    int iddanhsachChiTiet;
    int idbaihat;
    int iddanhsachPhat;

    public DanhSachChiTiet(int iddanhsachChiTiet, int idbaihat, int iddanhsachPhat) {
        this.iddanhsachChiTiet = iddanhsachChiTiet;
        this.idbaihat = idbaihat;
        this.iddanhsachPhat = iddanhsachPhat;
    }

    public int getIddanhsachChiTiet() {
        return iddanhsachChiTiet;
    }

    public void setIddanhsachChiTiet(int iddanhsachChiTiet) {
        this.iddanhsachChiTiet = iddanhsachChiTiet;
    }

    public int getIdbaihat() {
        return idbaihat;
    }

    public void setIdbaihat(int idbaihat) {
        this.idbaihat = idbaihat;
    }

    public int getIddanhsachPhat() {
        return iddanhsachPhat;
    }

    public void setIddanhsachPhat(int iddanhsachPhat) {
        this.iddanhsachPhat = iddanhsachPhat;
    }
}
