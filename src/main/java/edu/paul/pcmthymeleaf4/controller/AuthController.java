package edu.paul.pcmthymeleaf4.controller;

import edu.paul.pcmthymeleaf4.config.OtherConfig;
import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.RegisDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.VerifyRegisDTO;
import edu.paul.pcmthymeleaf4.httpclient.AuthService;
import edu.paul.pcmthymeleaf4.security.BcryptImpl;
import edu.paul.pcmthymeleaf4.utils.GenerateStringMenu;
import edu.paul.pcmthymeleaf4.utils.GlobalFunction;
import jakarta.validation.Valid;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
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

    @PostMapping("/regis")
    public String regis(Model model,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String namaLengkap,
                        @RequestParam String noHp,
                        @RequestParam String email,
                        @RequestParam String alamat,
                        @RequestParam String tanggalLahir,
                        @RequestParam MultipartFile file
                        ){
        password = new String(Base64.decode(password));

        Boolean isOk = regisValidation(model,username,password,namaLengkap,noHp,email,alamat,tanggalLahir);
        if(!isOk){
            setDataDefault(model,username,password,namaLengkap,noHp,email,alamat,tanggalLahir);
            return "auth/regis";
        }
        ResponseEntity<Object> response1 = null;
        ResponseEntity<Object> response2 = null;
        String otp = "";
        VerifyRegisDTO verifyRegisDTO = new VerifyRegisDTO();
        try{

            response1 = authService.registration(mapToRegisDTO(username,password,namaLengkap,noHp,email,alamat,tanggalLahir));
            System.out.println("Response 1 : "+response1.getBody());
            Map<String,Object> map = (Map<String, Object>) response1.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            String strId = mapData.get("id").toString();

            if(OtherConfig.getEnableAutomationTesting().equals("y")){
                Object obj = mapData.get("otp");
                otp = obj==null?"":obj.toString();
                verifyRegisDTO.setOtp(otp);
            }
            Long idUser = Long.parseLong(strId);
            response2 = authService.registrationUpload(idUser,file);
            System.out.println("Response 2 : "+response2.getBody());
        }catch (Exception e){
            e.getMessage();
        }
        verifyRegisDTO.setEmail(email);
        model.addAttribute("data",verifyRegisDTO);
        return "auth/verify-regis";
    }

    @PostMapping("/ver-regis")
    public String verifyRegis(
            @Valid @ModelAttribute("data") VerifyRegisDTO verifyRegisDTO,
            Model model){
        ResponseEntity<Object> response = null;
        try{
            response = authService.verifyRegis(verifyRegisDTO);
        }catch (Exception e){
            e.getMessage();
        }
        return "redirect:/";
    }

    private RegisDTO mapToRegisDTO(String username,String password,
                                   String namaLengkap, String noHp,String  email,
                                           String alamat,String tanggalLahir
    ){
        RegisDTO regisDTO = new RegisDTO();
        regisDTO.setUsername(username);
        regisDTO.setPassword(password);
        regisDTO.setNamaLengkap(namaLengkap);
        regisDTO.setNoHp(noHp);
        regisDTO.setEmail(email);
        regisDTO.setAlamat(alamat);
        regisDTO.setTanggalLahir(LocalDate.parse(tanggalLahir));
        return regisDTO;
    }

    private Boolean regisValidation(Model model, String username,String password,
                                   String namaLengkap, String noHp,String  email,
                                   String alamat,String tanggalLahir
    ){
        Boolean isValid = true;
        int intCounter = 0;
        int countFalse =0;
        intCounter = GlobalFunction.matchingPattern(model, username,"^([a-z0-9\\.]{8,16})$","usernameMessage",
                "Format Huruf kecil ,numeric dan titik saja min 8 max 16 karakter, ex : paulch.1234")==true?0:1;
        countFalse += intCounter;
        intCounter = GlobalFunction.matchingPattern(model, password,"^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$","passwordMessage",
                "Format minimal 1 angka, 1 huruf kecil, 1 huruf besar, 1 spesial karakter (_ \"Underscore\", - \"Hyphen\", # \"Hash\", atau $ \"Dollar\" atau @ \"At\") setelah 4 kondisi min 9 max 16 alfanumerik, contoh : aB4$12345")?0:1;
        countFalse += intCounter;
        intCounter = GlobalFunction.matchingPattern(model, namaLengkap,"^[a-zA-Z\\s]{4,70}$","namaLengkapMessage",
                "Hanya Alfabet dan spasi Minimal 4 Maksimal 70")?0:1;
        countFalse += intCounter;

        if(countFalse != 0){
            isValid = false;
        }
        return isValid;
    }

    private void setDataDefault(Model model, String username,String password,
                                    String namaLengkap, String noHp,String  email,
                                    String alamat,String tanggalLahir
    ){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        model.addAttribute("namaLengkap",namaLengkap);
        model.addAttribute("noHp",noHp);
        model.addAttribute("email",email);
        model.addAttribute("alamat",alamat);
        model.addAttribute("tanggalLahir",tanggalLahir);
    }
}
