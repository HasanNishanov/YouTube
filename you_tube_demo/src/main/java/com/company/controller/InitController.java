package com.company.controller;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/init")
public class InitController {

    @GetMapping("")
    public void test(){

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        messageSource.setDefaultEncoding("UTF-8"); // Add this
        System.out.println(messageSource.getMessage("email.exist", null,new Locale("en")));
        System.out.println(messageSource.getMessage("email.exist", null,new Locale("uz")));
        System.out.println(messageSource.getMessage("email.exist", null,new Locale("ru")));
    }
}
