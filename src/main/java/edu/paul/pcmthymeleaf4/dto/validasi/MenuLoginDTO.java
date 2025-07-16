package edu.paul.pcmthymeleaf4.dto.validasi;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuLoginDTO {

    private String nama;

    private String path;

    @JsonIgnore
    private String namaGroupMenu;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNamaGroupMenu() {
        return namaGroupMenu;
    }

    public void setNamaGroupMenu(String namaGroupMenu) {
        this.namaGroupMenu = namaGroupMenu;
    }
}
