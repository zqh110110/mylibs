package com.smcb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//启动事务
@SpringBootApplication
@MapperScan("com.smcb.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
