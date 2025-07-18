package edu.paul.pcmthymeleaf4.controller;


import edu.paul.pcmthymeleaf4.config.OtherConfig;
import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import edu.paul.pcmthymeleaf4.httpclient.AuthService;
import edu.paul.pcmthymeleaf4.security.BcryptImpl;
import edu.paul.pcmthymeleaf4.utils.GenerateStringMenu;
import edu.paul.pcmthymeleaf4.utils.GlobalFunction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;


/** WAKTU SUBMIT DATA UNTUK PROSES LOGIN, FORGOTPASSWORD, REGISTRASI */
@Controller
@RequestMapping("auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute("data") @Valid LoginDTO loginDTO,
                        BindingResult result, Model model, WebRequest request){

        String decodePassword = new String(Base64.decode(loginDTO.getPassword()));


        GlobalFunction.matchingPattern(decodePassword,"^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
                "password","Format Tidak Valid","data",result);

        Boolean isValid = false;
        if(OtherConfig.getEnableAutomationTesting().equals("y")){
            isValid = loginDTO.getCaptcha().equals(loginDTO.getHiddenCaptcha());
        }else {
            isValid = BcryptImpl.verifyHash(loginDTO.getCaptcha(), loginDTO.getHiddenCaptcha());
        }
        if(result.hasErrors() || !isValid){
            if(!isValid){
                model.addAttribute("captchaMessage", "Invalid Captcha");
            }
            GlobalFunction.getCaptchaLogin(loginDTO);
            model.addAttribute("data",loginDTO);
            return "auth/login";
        }
        loginDTO.setPassword(decodePassword);
        ResponseEntity<Object> response = null;
        String menuNavBar = "";
        String jwt = "";
        try{
            response = authService.login(loginDTO);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            jwt = (String) mapData.get("token");
            List<Map<String,Object>> listMenu = (List<Map<String, Object>>) mapData.get("menu");
            menuNavBar = new GenerateStringMenu().stringMenu(listMenu);

        }catch(Exception e){
            System.out.println("Error Login "+e.getMessage());
            model.addAttribute("data",loginDTO);
            model.addAttribute("notif","Error Choy !!");
            return "auth/login";
        }
        String username = loginDTO.getUsername();
        request.setAttribute("MENU_NAVBAR",menuNavBar,1);
        request.setAttribute("USR_NAME",username,1);
        request.setAttribute("JWT",jwt,1);

        model.addAttribute("MENU_NAVBAR",menuNavBar);
        model.addAttribute("USR_NAME",username);
        return "auth/home";
    }
}
