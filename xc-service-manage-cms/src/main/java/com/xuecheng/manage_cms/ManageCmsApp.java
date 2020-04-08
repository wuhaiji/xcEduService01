package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient //注册为服务发现
@EnableTransactionManagement
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")//扫描其他模块中的实体类
@ComponentScan(basePackages={"com.xuecheng.api"})//扫描其他模块中的接口
@ComponentScan(basePackages={"com.xuecheng.framework"})//common下的类
@ComponentScan(basePackages={"com.xuecheng.manage_cms"})//扫描本项目下的所有类

public class ManageCmsApp {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApp.class, args);
    }
}