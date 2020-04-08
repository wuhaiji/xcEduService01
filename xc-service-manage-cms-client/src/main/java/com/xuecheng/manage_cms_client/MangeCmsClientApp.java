package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author whj
 * @createTime 2020-02-24 22:56
 * @description
 **/
@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages={"com.xuecheng.framework"})//扫描common下的所有类
@ComponentScan(basePackages={"com.xuecheng.manage_cms_client"})
@SpringBootApplication
public class MangeCmsClientApp {
    public static void main(String[] args) {
        SpringApplication.run(MangeCmsClientApp.class,args);
    }
}
