package edu.paul.pcmthymeleaf4.dto.response;

public class RespProdukDTO {
    private Long id;
    private String nama;
    private String deskripsi;
    private String model;
    private String warna;
    private Integer stok;
    private String merk;
    private Long idKategoriProduk;
    private String namaKategoriProduk;
    private String deskripsiKategoriProduk;

    
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdKategoriProduk() {
        return idKategoriProduk;
    }

    public void setIdKategoriProduk(Long idKategoriProduk) {
        this.idKategoriProduk = idKategoriProduk;
    }

    public String getNamaKategoriProduk() {
        return namaKategoriProduk;
    }

    public void setNamaKategoriProduk(String namaKategoriProduk) {
        this.namaKategoriProduk = namaKategoriProduk;
    }

    public String getDeskripsiKategoriProduk() {
        return deskripsiKategoriProduk;
    }

    public void setDeskripsiKategoriProduk(String deskripsiKategoriProduk) {
        this.deskripsiKategoriProduk = deskripsiKategoriProduk;
    }
}
