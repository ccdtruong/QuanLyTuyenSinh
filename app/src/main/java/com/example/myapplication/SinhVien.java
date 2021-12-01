package com.example.myapplication;

public class SinhVien {
    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public boolean isGt() {
        return gt;
    }

    public void setGt(boolean gt) {
        this.gt = gt;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    int maSV;
    boolean gt;
    float diem;
    String hoTen, cmnd, sdt, email, ngaySinh, noiSinh, nganh, lop;

    public SinhVien() {

    }

    public SinhVien(int id, String name, float point, String date, String phone, String cmnd, String address, String lop, String nganh) {
        this.maSV = id;
        this.hoTen = name;
        this.diem = point;
        this.ngaySinh = date;
        this.sdt=phone;
        this.cmnd = cmnd;
        this.noiSinh=address;
        this.lop = lop;
        this.nganh = nganh;
    }

    public SinhVien(int maSV, boolean gt, float diem, String hoTen, String cmnd, String sdt, String email, String ngaySinh, String noiSinh, String nganh, String lop) {
        this.maSV = maSV;
        this.gt = gt;
        this.diem = diem;
        this.hoTen = hoTen;
        this.cmnd = cmnd;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.nganh = nganh;
        this.lop = lop;
    }
}
