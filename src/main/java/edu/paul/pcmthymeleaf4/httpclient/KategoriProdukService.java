package edu.paul.pcmthymeleaf4.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kategoriproduk-servicfe",url = "${host.rest.api}"+"/kategoriproduk")
public interface KategoriProdukService {

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);
}
