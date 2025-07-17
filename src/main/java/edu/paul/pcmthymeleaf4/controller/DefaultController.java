package edu.paul.pcmthymeleaf4.controller;


import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Random;

@Controller
@RequestMapping("/")
public class DefaultController {



    @GetMapping
    public String index(Model model){

        model.addAttribute("data", new LoginDTO());
        return "auth/login";
    }

    @GetMapping("/home")
    public String home(Model model, WebRequest request){

        String username = (String) request.getAttribute("USR_NAME",1);
        String menuNavbar = (String) request.getAttribute("MENU_NAVBAR",1);

        model.addAttribute("USR_NAME", username);
        model.addAttribute("MENU_NAVBAR", menuNavbar);
        return "auth/home";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }
}
