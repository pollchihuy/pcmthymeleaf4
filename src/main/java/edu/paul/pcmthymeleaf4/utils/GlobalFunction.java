package edu.paul.pcmthymeleaf4.utils;

import cn.apiclub.captcha.Captcha;
import edu.paul.pcmthymeleaf4.config.OtherConfig;
import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import edu.paul.pcmthymeleaf4.security.BcryptImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class GlobalFunction {
public static final Integer [] SIZE_COMPONENT = {2,3,5,10,15,20,30};

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

    public static Boolean matchingPattern(Model model,
                                       String value,String regex,
                                       String field,String message){
        Boolean isValid = Pattern.compile(regex).matcher(value).find();
        if(!isValid){
            model.addAttribute(field,message);
        }
        return isValid;
    }


    public void insertGlobalAttribut(Model model, WebRequest request, String pageName){
        String username = (String) request.getAttribute("USR_NAME",1);
        String menuNavbar = (String) request.getAttribute("MENU_NAVBAR",1);

        model.addAttribute("USR_NAME", username);
        model.addAttribute("MENU_NAVBAR", menuNavbar);
        model.addAttribute("PAGE_NAME",pageName);
    }

    public String tokenCheck(Model model, WebRequest request){
        Object tokenJwt = request.getAttribute("JWT",1);
        if(tokenJwt == null){
            return "redirect:/";
        }
        return "Bearer "+tokenJwt;
    }

    /** fungsi ini hanya digunakan jika penulisan variable menggunakan convention naming java */
    public static String camelToStandard(String camel){
        StringBuilder sb = new StringBuilder();
        char [] chArr = camel.toCharArray();

        for (int i = 0; i < chArr.length; i++) {
            char c1 = chArr[i];
            if(Character.isUpperCase(c1)){
                sb.append(' ').append(Character.toLowerCase(c1));// mengubah karakter yang sebelumnya huruf kapital menjadi huruf kecil
            }
            else {
                sb.append(c1);
            }
        }
        return sb.toString().toUpperCase();
    }

    public void generateMainPage(Model model, Map<String,Object> mapData,String pathServer,Map<String,String> filterColumn){
        List<Map<String,Object>> list = (List<Map<String, Object>>) mapData.get("content");
        List<String> listKolom = new LinkedList<>();
        List<String> listHelper = new ArrayList<>();
        Map<String,Object> columnHeader = list.get(0);
        String keyVal = "";
        for(Map.Entry<String,Object> entry : columnHeader.entrySet()){
            keyVal = entry.getKey();
            listHelper.add(keyVal);
            listKolom.add(GlobalFunction.camelToStandard(keyVal));
        }
        model.addAttribute("listKolom",listKolom);
        model.addAttribute("listContent",list);
        model.addAttribute("listHelper",listHelper);

        model.addAttribute("pathServer",pathServer);

//        "total_data": 6,
//                "column_name": "id",
//                "size_per_page": 50,
//                "total_pages": 1,
//                "sort": "asc",
//                "sort_by": "id",
//                "value": "",
//        "current_page": 0
        int currentPage = (int) mapData.get("current_page");
        model.addAttribute("sort",mapData.get("sort"));
        model.addAttribute("sortBy",mapData.get("sort_by"));
        model.addAttribute("currentPage",(currentPage+1));
        model.addAttribute("columnName",mapData.get("column_name"));
        model.addAttribute("value",mapData.get("value"));
        model.addAttribute("totalPage",mapData.get("total_pages"));
        model.addAttribute("sizePerPage",mapData.get("size_per_page"));
        model.addAttribute("totalData",mapData.get("total_data"));
        model.addAttribute("SIZE_COMPONENT",SIZE_COMPONENT);
        model.addAttribute("filterColumn",filterColumn);




    }
}
