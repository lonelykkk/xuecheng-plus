package com.xuecheng.content;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableSwagger2Doc
@ComponentScan(basePackages = {"com.xuecheng.content", "com.xuecheng.messagesdk"})
@EnableFeignClients(basePackages={"com.xuecheng.content.feignclient"})
public class ContentApplication {
   public static void main(String[] args) {
      SpringApplication.run(ContentApplication.class, args);
   }
}