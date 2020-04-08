package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author whj
 * @createTime 2020-03-09 10:22
 * @description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFeign {
    @Autowired
    CmsPageClient cmsPageClient;//接口的代理对象，由feign远程调用
    @Test
    public void testFei(){

    }
}
