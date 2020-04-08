package com.xuecheng.manage_course.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author whj
 * @createTime 2020-03-09 10:22
 * @description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRibbon {

    @Autowired
    RestTemplate restTemplate;
    @Test
    public void testRi(){
        for (int i = 0; i < 10; i++) {
            String serverName="XC-SERVICE-MANAGE-CMS";
            String url = "http://"+serverName+"/cms/page/get/5e3cd69972158d7de4927478";
            Map forObject = restTemplate.getForObject(url, Map.class);
            System.out.println(forObject);
        }

    }
}
