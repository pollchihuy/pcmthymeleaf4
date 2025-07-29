package edu.paul.pcmthymeleaf4.dto.validasi;

import edu.paul.pcmthymeleaf4.dto.relation.RelKategoriProdukDTO;
import edu.paul.pcmthymeleaf4.dto.relation.RelSupplierDTO;

import java.util.List;

public class ValProdukComponentDTO {
    
    private String nama;
    private String deskripsi;
    private String model;
    private String warna;
    private Integer stok;
    private String merk;
    private RelKategoriProdukDTO kategoriProduk;
    private List<String> suppliers;

    public List<String> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<String> suppliers) {
        this.suppliers = suppliers;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public RelKategoriProdukDTO getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(RelKategoriProdukDTO kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }
}
