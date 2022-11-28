package com.example.mylaund;

public class Order {
    private int order_number;
    private int order_id;
    private String alamat;
    private String jenis_cucian;
    private int seterika;
    private int durasi;

    public Order(){}
    public Order(int order_id, String alamat, String jenis_cucian, int seterika, int durasi, int order_number){
        this.order_number = order_number;
        this.order_id = order_id;
        this.alamat = alamat;
        this.jenis_cucian = jenis_cucian;
        this.seterika = seterika;
        this.durasi = durasi;
    }


    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_cucian() {
        return jenis_cucian;
    }

    public void setJenis_cucian(String jenis_cucian) {
        this.jenis_cucian = jenis_cucian;
    }

    public int getSeterika() {
        return seterika;
    }

    public void setSeterika(int seterika) {
        this.seterika = seterika;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }


}
