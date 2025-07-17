package edu.paul.pcmthymeleaf4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contohsatu")
public class ContohSatuController {

    @GetMapping
    public String defaults(){
        return "contoh/contoh-satu";
    }

    @GetMapping("/satu")
    public String contohSatuSatu(){
        return "contoh/contoh-satu-satu";
    }

    @GetMapping("/satu/{dataPassing}")
    public String contohSatuDua(
            Model model,
            @PathVariable String dataPassing
    ){
        model.addAttribute("dataCoba", dataPassing);
        return "contoh/contoh-satu-dua";
    }

    @GetMapping("/one/{conditionz}")
    public String contohSatuTiga(
            Model model,
            @PathVariable String conditionz
    ){
        String strRedirect = "";
        switch(conditionz){
            case "1":strRedirect="/err/400";break;
            case "2":strRedirect="/err/500";break;
            default:strRedirect="/err/401";break;
        }

        return "redirect:"+strRedirect;
//        return "redirect:/contohsatu/satu/"+conditionz;
    }
}