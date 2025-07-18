package edu.paul.pcmthymeleaf4.utils;

import cn.apiclub.captcha.Captcha;
import edu.paul.pcmthymeleaf4.config.OtherConfig;
import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import edu.paul.pcmthymeleaf4.security.BcryptImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;

import java.util.regex.Pattern;

public class GlobalFunction {


    public static void getCaptchaLogin(LoginDTO loginDTO){
        Captcha captcha = CaptchaUtils.createCaptcha(200,50);
        String answer = captcha.getAnswer();
        if(OtherConfig.getEnableAutomationTesting().equals("y")){
            loginDTO.setHiddenCaptcha(answer);
        }else {
            loginDTO.setHiddenCaptcha(BcryptImpl.hash(answer));
        }
        loginDTO.setCaptcha("");
        loginDTO.setRealCaptcha(CaptchaUtils.encodeCaptcha(captcha));
    }

    public static void matchingPattern(String value,String regex,
                                       String field,String message,
                                       String modelAttribut,
                                       BindingResult result){
        Boolean isValid = Pattern.compile(regex).matcher(value).find();
        if(!isValid){
            result.rejectValue(field,"error."+modelAttribut,message);
        }
    }


    public void insertGlobalAttribut(Model model, WebRequest request, String pageName){
        String username = (String) request.getAttribute("USR_NAME",1);
        String menuNavbar = (String) request.getAttribute("MENU_NAVBAR",1);

        model.addAttribute("USR_NAME", username);
        model.addAttribute("MENU_NAVBAR", menuNavbar);
        model.addAttribute("PAGE_NAME",pageName);
    }
}
