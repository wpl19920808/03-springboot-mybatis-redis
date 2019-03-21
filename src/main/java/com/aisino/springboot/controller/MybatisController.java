package com.aisino.springboot.controller;

import com.aisino.springboot.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class MybatisController {
    @Autowired
    private PersonService personService;

    @GetMapping("boot/person")
    public Object person(){
        // 多线程测试缓存穿透问题，该线程调用底层selectAllPerson方法
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10000; ++i) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    personService.getAllPerson();
                }
            });
        }

        return personService.getAllPerson();
    }

    @GetMapping("boot/update")
    public Object update(){
        return personService.update();
    }
}
