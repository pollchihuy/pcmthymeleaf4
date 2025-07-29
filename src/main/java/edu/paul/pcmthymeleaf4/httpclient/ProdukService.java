package edu.paul.pcmthymeleaf4.httpclient;

import edu.paul.pcmthymeleaf4.dto.validasi.ValKategoriProdukDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.ValProdukDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "produk-service",url = "${host.rest.api}"+"/produk")
public interface ProdukService {

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
                                        @RequestBody ValProdukDTO valProdukDTO);

    @GetMapping("/{id}")
    public ResponseEntity<Object>  findById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<Object>  update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody ValProdukDTO valProdukDTO);
}
