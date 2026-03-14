package com.dingyaoming.dingseckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.dingyaoming.dingseckill.mapper")
public class DingSeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(DingSeckillApplication.class,args);
    }
}
