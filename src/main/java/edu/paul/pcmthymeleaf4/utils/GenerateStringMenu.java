package edu.paul.pcmthymeleaf4.utils;

import java.util.List;
import java.util.Map;

public class GenerateStringMenu {

    private StringBuilder sBuilder = new StringBuilder();

    public String stringMenu(List<Map<String,Object>> lt){
        sBuilder.setLength(0);
        for(Map<String,Object> map : lt){
            sBuilder.append("<li><a href=\"#\">"+map.get("group")+"</a>")//ini adalah menu header nya - pembuka
                    .append("<ul class=\"dropdown\">");
            List<Map<String,Object>> subMenu = (List<Map<String, Object>>) map.get("subMenu");
            for(Map<String,Object> subMenuMap : subMenu){
                sBuilder.append("<li><a href=\""+subMenuMap.get("path")+"\">").
                        append(subMenuMap.get("nama").toString()).append("</a></li>");
            }
            sBuilder.append("</ul></li>");//ini adalah menu header nya - penutup
        }
        return sBuilder.toString();
    }
}