package org.example.t5s;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan(basePackages = "org.example.t5s.controller")
public class T5SApplication {

    public static void main(String[] args) {
        SpringApplication.run(T5SApplication.class, args);
    }

}
