package edu.paul.pcmthymeleaf4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("err")
public class ErrorResponseController {

    @GetMapping("/400")
    public String return400(){
        return "err-response/400";
    }
    @GetMapping("/500")
    public String return500(){
        return "err-response/500";
    }

    @GetMapping("/401")
    public String return401(){
        return "err-response/401";
    }
}
