package edu.paul.pcmthymeleaf4.dto.validasi;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValSupplierDTO {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]{10,50}$",message = "Nama Supplier Alfabet dan Spasi Min 10 Maks 50 Ex: Elektronik Static")
    private String nama;

    @NotNull
    @Pattern(regexp = "^[\\w\\s\\.,-]{20,255}$",message = "Alamat Supplier Alfabetnumeric spasi titik koma dan hyphen Min 20 Maks 255 Ex: Jln Kenanga blok C no.180 11480")
    private String alamat;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
