package edu.paul.pcmthymeleaf4.dto.validasi;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class LupaPasswordStepTwoDTO {

    @Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,50})+$",
            message = "Format tidak valid ex : user_name123@sub.domain.com")
    private String email;

    @Length(min = 60, max = 64, message = "Request Tidak Valid")
    private String tokenEstafet;

    @Pattern(regexp = "^[0-9]{6}$",message = "Format OTP Tidak Valid")
    private String otp;

    public @Length(min = 60, max = 64, message = "Request Tidak Valid") String getTokenEstafet() {
        return tokenEstafet;
    }

    public void setTokenEstafet(@Length(min = 60, max = 64, message = "Request Tidak Valid") String tokenEstafet) {
        this.tokenEstafet = tokenEstafet;
    }

    public @Pattern(regexp = "^[0-9]{6}$", message = "Format OTP Tidak Valid") String getOtp() {
        return otp;
    }

    public void setOtp(@Pattern(regexp = "^[0-9]{6}$", message = "Format OTP Tidak Valid") String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
