package com.example.myapplication;

public class NGANH {
    int maNganh;
    float diemChuan;
    String tenNganh, khoa, thoiGianDaoTao;

    public NGANH(int maNganh, float diemChuan, String tenNganh, String khoa, String thoiGianDaoTao) {
        this.maNganh = maNganh;
        this.diemChuan = diemChuan;
        this.tenNganh = tenNganh;
        this.khoa = khoa;
        this.thoiGianDaoTao = thoiGianDaoTao;
    }

    public NGANH(float diemChuan, String tenNganh, String khoa, String thoiGianDaoTao) {
        this.diemChuan = diemChuan;
        this.tenNganh = tenNganh;
        this.khoa = khoa;
        this.thoiGianDaoTao = thoiGianDaoTao;
    }

    public NGANH() {
    }

    public int getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(int maNganh) {
        this.maNganh = maNganh;
    }

    public float getDiemChuan() {
        return diemChuan;
    }

    public void setDiemChuan(float diemChuan) {
        this.diemChuan = diemChuan;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getThoiGianDaoTao() {
        return thoiGianDaoTao;
    }

    public void setThoiGianDaoTao(String thoiGianDaoTao) {
        this.thoiGianDaoTao = thoiGianDaoTao;
    }
}
