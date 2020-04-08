package com.xuecheng.manage_cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author whj
 * @createTime 2020-02-10 18:53
 * @description
 **/
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        OkHttp3ClientHttpRequestFactory factory =new OkHttp3ClientHttpRequestFactory();
        //默认的是JDK提供http,这里替换为例如Apache OkHttp
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return new RestTemplate(factory);
    }
}
