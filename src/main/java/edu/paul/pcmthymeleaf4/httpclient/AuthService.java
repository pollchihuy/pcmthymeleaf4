package edu.paul.pcmthymeleaf4.httpclient;


import edu.paul.pcmthymeleaf4.dto.validasi.LoginDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.RegisDTO;
import edu.paul.pcmthymeleaf4.dto.validasi.VerifyRegisDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "auth-services",url = "${host.rest.api}"+"/auth")
public interface AuthService {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO);

    @PostMapping("/regis")
    public ResponseEntity<Object> registration(@RequestBody RegisDTO regisDTO);

    @PostMapping(value = "/regis/upload/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> registrationUpload(@PathVariable Long id,@RequestPart MultipartFile file);

    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegis(@RequestBody VerifyRegisDTO verifyRegisDTO);
}