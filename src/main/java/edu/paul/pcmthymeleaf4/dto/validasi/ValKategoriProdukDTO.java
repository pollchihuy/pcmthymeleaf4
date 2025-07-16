package edu.paul.pcmthymeleaf4.dto.validasi;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValKategoriProdukDTO {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]{10,50}$",message = "Nama Kategori Produk Alfabet dan Spasi Min 10 Maks 50 Ex: Elektronik Static")
    private String nama;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]{20,255}$",message = "Deskripsi Kategori Produk Alfabet dan Spasi Min 20 Maks 255 Ex: Untuk Seluruh produk yang memiliki kategori Elektronik Static ")
    private String deskripsi;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]{20,255}$",message = "Catatan Kategori Produk Alfabet dan Spasi Min 20 Maks 255 Ex: Ini hanya Catatan Saja Untuk Contoh")
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}
