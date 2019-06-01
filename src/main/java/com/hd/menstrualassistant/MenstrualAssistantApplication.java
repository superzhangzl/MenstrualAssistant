package com.hd.menstrualassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class MenstrualAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenstrualAssistantApplication.class, args);
    }


    @GetMapping
    @ResponseBody
    public String test(){
        return "hello";
    }

}
