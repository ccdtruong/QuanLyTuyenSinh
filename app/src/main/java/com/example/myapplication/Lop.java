package com.example.myapplication;

public class Lop {
    int MaLop;
    String tenLop, GV, Nganh, Khoa, Siso;

    public Lop() {
    }
    public Lop(int maLop, String tenLop, String GV, String nganh, String khoa, String siso) {
        MaLop = maLop;
        this.tenLop = tenLop;
        this.GV = GV;
        Nganh = nganh;
        Khoa = khoa;
        Siso = siso;
    }

    public int getMaLop() {
        return MaLop;
    }

    public void setMaLop(int maLop) {
        MaLop = maLop;
    }

    public String  getSiso() {
        return Siso;
    }

    public void setSiso(String siso) {
        Siso = siso;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getGV() {
        return GV;
    }

    public void setGV(String GV) {
        this.GV = GV;
    }

    public String getNganh() {
        return Nganh;
    }

    public void setNganh(String nganh) {
        Nganh = nganh;
    }

    public String getKhoa() {
        return Khoa;
    }

    public void setKhoa(String khoa) {
        Khoa = khoa;
    }
}
