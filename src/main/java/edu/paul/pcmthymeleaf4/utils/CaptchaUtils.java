package edu.paul.pcmthymeleaf4.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class CaptchaUtils {


    public static Captcha createCaptcha(Integer width, Integer height){
        return new Captcha.Builder(width,height)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new DefaultTextProducer(),new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
    }

    public static String encodeCaptcha(Captcha captcha){
        String image = null;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(),"jpg",baos);
            byte[] byteArray = Base64.getEncoder().encode(baos.toByteArray());
            image = new String(byteArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }
}
