package edu.paul.pcmthymeleaf4.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.paul.pcmthymeleaf4.dto.response.RespKategoriProdukDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.ValSupplierDTO;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;
    private Map<String,String> filterColumn= new HashMap<>();

    public SupplierController() {

        filterColumn.put("nama","NAMA");
        filterColumn.put("alamat","Alamat");
//        filterColumn.put("notes","NOTES");
    }

    @GetMapping
    public String defaultPage(Model model, WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try {
            response = supplierService.findAll(jwt);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            new GlobalFunction().generateMainPage(model,mapData,"supplier",filterColumn);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        new GlobalFunction().insertGlobalAttribut(model,request,"SUPPLIER");
        return "main";
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    public String findByParam(
            Model model,
            @PathVariable Integer page,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable String sort,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        page = page!=0?(page-1):page;// untuk menjadikan nilai absolut
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try {
            response = supplierService.findByParam(jwt,page,sortBy,sort,size,column,value);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            new GlobalFunction().generateMainPage(model,mapData,"supplier",filterColumn);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        new GlobalFunction().insertGlobalAttribut(model,request,"SUPPLIER");
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
            response = supplierService.downloadExcel(jwt,column,value);
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
            response = supplierService.downloadPdf(jwt,column,value);
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

        model.addAttribute("data",new ValSupplierDTO());
        return "supplier/add";
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
            response = supplierService.findById(jwt,id);
            map = (Map<String, Object>) response.getBody();
            mapData = (Map<String, Object>) map.get("data");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        model.addAttribute("data",new ObjectMapper().convertValue(mapData, RespKategoriProdukDTO.class));
        model.addAttribute("ids",id);
        return "supplier/edit";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("data") ValSupplierDTO valSupplierDTO,
            BindingResult bindingResult,
            Model model,WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("data",valSupplierDTO);
            return "supplier/add";
        }
        try {
            response = supplierService.save(jwt,valSupplierDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "err-response/200";
    }

    @PostMapping("/{id}")
    public String edit(
                                 @Valid @ModelAttribute("data") ValSupplierDTO valSupplierDTO,
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
            model.addAttribute("data",valSupplierDTO);
            model.addAttribute("ids",id);
            return "supplier/edit";
        }
        try {
            response = supplierService.update(jwt,id,valSupplierDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "err-response/200";
    }
}