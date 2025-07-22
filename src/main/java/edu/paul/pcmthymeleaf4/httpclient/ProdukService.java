package edu.paul.pcmthymeleaf4.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "produk-servicfe",url = "${host.rest.api}"+"/produk")
public interface ProdukService {

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);
}
