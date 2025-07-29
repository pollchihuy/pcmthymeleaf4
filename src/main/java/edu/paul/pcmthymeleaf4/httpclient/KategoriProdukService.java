package edu.paul.pcmthymeleaf4.httpclient;

import edu.paul.pcmthymeleaf4.dto.validasi.ValKategoriProdukDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "kategoriproduk-service",url = "${host.rest.api}"+"/kategoriproduk")
public interface KategoriProdukService {

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);

    @GetMapping("/{sort}/{sort-by}/{page}")
    public ResponseEntity<Object>  findByParam(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer page,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable String sort,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value);

    @GetMapping("/download-excel")
    public Response downloadExcel(
            @RequestHeader("Authorization") String token,
            @RequestParam String column,
            @RequestParam String value);

    @GetMapping("/download-pdf")
    public Response downloadPdf(
            @RequestHeader("Authorization") String token,
            @RequestParam String column,
            @RequestParam String value);

    @PostMapping
    public ResponseEntity<Object>  save(@RequestHeader("Authorization") String token,
                                        @RequestBody ValKategoriProdukDTO valKategoriProdukDTO);

    @GetMapping("/{id}")
    public ResponseEntity<Object>  findById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<Object>  update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody ValKategoriProdukDTO valKategoriProdukDTO);
}
