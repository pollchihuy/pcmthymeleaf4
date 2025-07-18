package edu.paul.pcmthymeleaf4.controller;


import edu.paul.pcmthymeleaf4.utils.GlobalFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    @GetMapping
    public String defaultPage(Model model, WebRequest request){
        new GlobalFunction().insertGlobalAttribut(model,request,"KATEGORI PRODUK");
        return "main";
    }
}
