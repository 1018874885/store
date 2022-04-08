package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
//指定当前项目中Mapper接口路径的位置，项目启动时会自动加载所有的接口
@MapperScan({"com.cy.store.mapper" , "com.cy.store.mybatis.dao.auto"})
@Configuration  //表示配置类
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    //主启动类的方法需要@Bean注解
    //此方法用于配置MultipartFile上传的文件大小
    @Bean
    public MultipartConfigElement getMultipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // DataSize dataSize = DataSize.ofMegabytes(10);
        // 设置文件最大10M，DataUnit提供5中类型B,KB,MB,GB,TB
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));  //设置上传的文件大小
        factory.setMaxRequestSize(DataSize.of(10, DataUnit.MEGABYTES)); //设置上传的请求大小
        // 设置总上传数据总大小10M
        return factory.createMultipartConfig();
    }
}
