package com.dingyaoming.dingseckill;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public HelloController() {
        System.out.println(">>> HelloController 被Spring加载了！");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Ding! Day1完成";
    }
}
