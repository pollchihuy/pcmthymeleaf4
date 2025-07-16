package edu.paul.pcmthymeleaf4.controller;

import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("contohdua")
public class ContohDuaController {

    @GetMapping
    public String defaults(){
        return "contoh/contoh-dua-satu";
    }

    @GetMapping("/sbmt")
    public String submitForm(
            Model model,
            @RequestParam String textOne,
            @RequestParam MultipartFile file
    ){
        model.addAttribute("textOne", textOne+" Berhasil");
        model.addAttribute("dataInput", textOne);

        return "contoh/contoh-dua-satu";
    }

    @GetMapping("/two")
    public String contohDuaDua(Model model){

        model.addAttribute("data", new LoginDTO());
        return "contoh/contoh-dua-dua";
    }

    @PostMapping("/two/sbmt")
    public String contohDuaDuaSubmit(
                                     @ModelAttribute("data") @Valid LoginDTO loginDTO,
                                     BindingResult bindingResult,
                                     Model model
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("data", loginDTO);
            return "contoh/contoh-dua-dua";
        }
//        String username=loginDTO.getUsername();
//        String password=loginDTO.getPassword();
//        loginDTO.setUsername(username+" Berhasil");
//        loginDTO.setPassword(password+" Berhasil");
//
//        model.addAttribute("data", loginDTO);
//        model.addAttribute("dataUsername", username);
//        model.addAttribute("dataPassword", password);
        return "/contoh/contoh-dua-tiga";
    }

}