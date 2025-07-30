package edu.paul.pcmthymeleaf4.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.paul.pcmthymeleaf4.dto.response.RespKategoriProdukDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.SelectSupplierDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.ValKategoriProdukDTO;
import edu.paul.pcmthymeleaf4.httpclient.KategoriProdukService;
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
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    @Autowired
    KategoriProdukService kategoriProdukService;
    private Map<String,String> filterColumn= new HashMap<>();

    public KategoriProdukController() {

        filterColumn.put("nama","NAMA");
        filterColumn.put("deskripsi","DESKRIP");
        filterColumn.put("notes","NOTES");
    }

    @GetMapping
    public String defaultPage(Model model, WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        try {
            response = kategoriProdukService.findAll(jwt);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            new GlobalFunction().generateMainPage(model,mapData,"kategoriproduk",filterColumn);

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
            response = kategoriProdukService.findByParam(jwt,page,sortBy,sort,size,column,value);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            new GlobalFunction().generateMainPage(model,mapData,"kategoriproduk",filterColumn);
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
            response = kategoriProdukService.downloadExcel(jwt,column,value);
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
            response = kategoriProdukService.downloadPdf(jwt,column,value);
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

        model.addAttribute("data",new ValKategoriProdukDTO());
        return "kategoriproduk/add";
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
            response = kategoriProdukService.findById(jwt,id);
            map = (Map<String, Object>) response.getBody();
            mapData = (Map<String, Object>) map.get("data");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        model.addAttribute("data",new ObjectMapper().convertValue(mapData, RespKategoriProdukDTO.class));
        model.addAttribute("ids",id);
        return "kategoriproduk/edit";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("data") ValKategoriProdukDTO valKategoriProdukDTO,
            BindingResult bindingResult,
            Model model,WebRequest request){
        ResponseEntity<Object> response = null;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("data",valKategoriProdukDTO);
            return "kategoriproduk/add";
        }
        try {
            response = kategoriProdukService.save(jwt,valKategoriProdukDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "err-response/200";
    }

    /**
     *
     * @param valKategoriProdukDTO
     * @param bindingResult
     * @param model
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/{id}")
    public String edit(
                                 @Valid @ModelAttribute("data") ValKategoriProdukDTO valKategoriProdukDTO,
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
            model.addAttribute("data",valKategoriProdukDTO);
            model.addAttribute("ids",id);
            return "kategoriproduk/edit";
        }
        try {
            response = kategoriProdukService.update(jwt,id,valKategoriProdukDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "err-response/200";
    }




    @GetMapping("{idComp}/{descComp}/{sort}/{sortBy}/{page}")
    public String dataTable(Model model,
                            @PathVariable(value = "sort") String sort,
                            @PathVariable(value = "sortBy") String sortBy,//name
                            @PathVariable(value = "page") Integer page,
                            @RequestParam(value = "size") Integer size,
                            @RequestParam(value = "column") String column,
                            @RequestParam(value = "value") String value,
                            @PathVariable(value = "idComp") String idComp,
                            @PathVariable(value = "descComp") String descComp,
                            WebRequest request){
        ResponseEntity<Object> response = null;
        page = page!=0?(page-1):page;
        String jwt = new GlobalFunction().tokenCheck(model,request);
        if(jwt.equals("redirect:/")){
            return jwt;
        }
        Map<String,Object> map = null;
        Map<String,Object> mapData = null;
        try{

            response = kategoriProdukService.findByParam(jwt,page,sortBy,sort,size,column,value);
            map = (Map<String, Object>) response.getBody();
            mapData = (Map<String, Object>) map.get("data");
        }catch (Exception e){
            model.addAttribute("idComp", idComp);
            model.addAttribute("descComp",descComp);
            return "global/data-table-form";
        }

        new GlobalFunction().generateMainPage(model,mapData,"kategoriproduk",filterColumn);
        new GlobalFunction().insertGlobalAttribut(model,request,"KATEGORI PRODUK");
        model.addAttribute("idComp", idComp);
        model.addAttribute("descComp",descComp);
        return "data-table-form";
    }
}