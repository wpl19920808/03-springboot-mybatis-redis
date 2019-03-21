package com.aisino.springboot.controller;

import com.aisino.springboot.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MybatisController {
    @Autowired
    private PersonService personService;

    @GetMapping("boot/person")
    public Object person(){
        return personService.getAllPerson();
    }
}
