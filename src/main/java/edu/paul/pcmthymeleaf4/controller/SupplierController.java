package edu.paul.pcmthymeleaf4.controller;


import edu.paul.pcmthymeleaf4.httpclient.SupplierService;
import edu.paul.pcmthymeleaf4.utils.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@Controller
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    private Map<String,String> filterColumn= new HashMap<>();

    public SupplierController() {

        filterColumn.put("nama","NAMA");
        filterColumn.put("deskripsi","DESKRIP");
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
}
