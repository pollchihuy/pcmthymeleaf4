package edu.paul.pcmthymeleaf4.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.paul.pcmthymeleaf4.dto.relation.RelSupplierDTO;
import edu.paul.pcmthymeleaf4.dto.response.RespKategoriProdukDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.ValProdukComponentDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.ValProdukDTO;
import edu.paul.pcmthymeleaf4.httpclient.ProdukService;
import edu.paul.pcmthymeleaf4.httpclient.SupplierService;
import edu.paul.pcmthymeleaf4.utils.GlobalFunction;
import feign.Response;
import jakarta.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("produk")
public class ProdukController {

    @Autowired
    ProdukService produkService;

    @Autowired
    SupplierService supplierService;

    private Map<String,String> filterColumn= new HashMap<>();

    public ProdukController() {

        filterColumn.put("nama","NAMA");
        filterColumn.put("deskripsi","DESKRIPSI");
        filterColumn.put("warna","WARNA");
        filterColumn.put("merk","MERK");
        filterColumn.put("model","MODEL");
    }

    @GetMapping
    public String defaultPage(Model model, WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try {
            response = produkService.findAll(jwt);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            new GlobalFunction().generateMainPage(model,mapData,"produk",filterColumn);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        new GlobalFunction().insertGlobalAttribut(model,request,"KATEGORI PRODUK");
        return "main";
    }

//    @GetMapping("/{sort}/{sort-by}/{page}/{totalData}")
    @GetMapping("/{sort}/{sort-by}/{page}")
    public String findByParam(
            Model model,
            @PathVariable Integer page,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable String sort,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
//            @PathVariable String totalData,
            WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
//        int intTotalData = Integer.parseInt(totalData);
        page = page!=0?(page-1):page;// untuk menjadikan nilai absolut
//        page = ((page*size)>intTotalData)?0:page;

        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try {
            response = produkService.findByParam(jwt,page,sortBy,sort,size,column,value);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            new GlobalFunction().generateMainPage(model,mapData,"produk",filterColumn);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        new GlobalFunction().insertGlobalAttribut(model,request,"KATEGORI PRODUK");
        return "main";
    }

    @GetMapping("/excel")
    public Object downloadExcel(
           Model model,
            @RequestParam String column,
            @RequestParam String value,
           WebRequest request){
        ByteArrayResource resource = null;
        String fileName = "";
        Response response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try{
            response = produkService.downloadExcel(jwt,column,value);
            fileName = response.headers().get("Content-Disposition").toString();
            InputStream inputStream = response.body().asInputStream();
            resource = new ByteArrayResource(IOUtils.toByteArray(inputStream));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition",fileName.substring(0,fileName.length()-1));

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).
                body(resource);
    }

    @GetMapping("/pdf")
    public Object downloadPdf(
            Model model,
            @RequestParam String column,
            @RequestParam String value,
            WebRequest request){
        ByteArrayResource resource = null;
        String fileName = "";
        Response response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try{
            response = produkService.downloadPdf(jwt,column,value);
            fileName = response.headers().get("Content-Disposition").toString();
            InputStream inputStream = response.body().asInputStream();
            resource = new ByteArrayResource(IOUtils.toByteArray(inputStream));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition",fileName.substring(0,fileName.length()-1));

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/pdf")).
                body(resource);
    }

    @GetMapping("/a")
    public String openModalsAdd(Model model,WebRequest request){
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        List<Map<String,Object>> x = null;
        try{
            x = getListSupplier(supplierService,jwt);
//            int xGituAjah = 1/0;
        }catch (Exception e){
            model.addAttribute("data",new ValProdukDTO());
            model.addAttribute("x",x);
//            System.out.println("Error open modals add"+e.getMessage());
            return "produk/add";
        }

        model.addAttribute("data",new ValProdukDTO());
        model.addAttribute("x",x);
        return "produk/add";
    }

    private List<Map<String,Object>> getListSupplier(SupplierService supplierService,String jwt){
        ResponseEntity<Object> response = supplierService.findAll(jwt);
        Map<String,Object> map = (Map<String, Object>) response.getBody();
        Map<String,Object> mapData = (Map<String, Object>) map.get("data");
        return (List<Map<String, Object>>) mapData.get("content");
    }

    @GetMapping("/e/{id}")
    public String openModalsEdit(Model model,
                                 @PathVariable Long id,
                                 WebRequest request){
        ResponseEntity<Object> response = null;
        Map<String,Object> map = null;
        Map<String,Object> mapData = null;
                String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try {
            response = produkService.findById(jwt,id);
            map = (Map<String, Object>) response.getBody();
            mapData = (Map<String, Object>) map.get("data");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        model.addAttribute("data",new ObjectMapper().convertValue(mapData, RespKategoriProdukDTO.class));
        model.addAttribute("ids",id);
        return "produk/edit";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("data") ValProdukComponentDTO valProdukComponentDTO,
            BindingResult bindingResult,
            Model model,WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }
       ValProdukDTO valProdukDTO = mapToProdukDTO(valProdukComponentDTO);
        if(bindingResult.hasErrors()){
            model.addAttribute("data",valProdukDTO);
            model.addAttribute("x",getListSupplier(supplierService,jwt));
            return "produk/add";
        }
        try {
            response = produkService.save(jwt,valProdukDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("data",valProdukDTO);
            model.addAttribute("x",getListSupplier(supplierService,jwt));
            return "produk/add";
        }
        return "err-response/200";
    }

    private ValProdukDTO mapToProdukDTO(ValProdukComponentDTO valProdukComponentDTO){
        ValProdukDTO valProdukDTO = new ValProdukDTO();
        valProdukDTO.setKategoriProduk(valProdukComponentDTO.getKategoriProduk());
        valProdukDTO.setMerk(valProdukComponentDTO.getMerk());
        valProdukDTO.setNama(valProdukComponentDTO.getNama());
        valProdukDTO.setWarna(valProdukComponentDTO.getWarna());
        valProdukDTO.setStok(valProdukComponentDTO.getStok());
        valProdukDTO.setDeskripsi(valProdukComponentDTO.getDeskripsi());
        valProdukDTO.setModel(valProdukComponentDTO.getModel());
        List<String> ls = valProdukComponentDTO.getSuppliers();
        List<RelSupplierDTO> lsVal = new ArrayList<>();
        for (String s: ls) {
            RelSupplierDTO relSupplierDTO = new RelSupplierDTO();
            relSupplierDTO.setId(Long.parseLong(s));
            lsVal.add(relSupplierDTO);
        }
        valProdukDTO.setSuppliers(lsVal);
        return valProdukDTO;
    }

    @PostMapping("/{id}")
    public String edit(
                                 @Valid @ModelAttribute("data") ValProdukDTO valProdukDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 @PathVariable Long id,
                                 WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("data",valProdukDTO);
            model.addAttribute("ids",id);
            return "produk/edit";
        }
        try {
            response = produkService.update(jwt,id,valProdukDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "err-response/200";
    }
}