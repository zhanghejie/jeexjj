package com.xjj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.xjj.**.dao")
@ComponentScan("com.xjj")
public class App{

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }
}