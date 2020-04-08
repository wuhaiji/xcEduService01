package com.xuecheng.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author whj
 * @createTime 2020-02-23 14:59
 * @description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    private CmsPageService cmsPageService;
    @Test
    public void testGenrateHtml() throws IOException {
        String pageHtml = cmsPageService.getPageHtml("5e3cd69972158d7de4927478");
        System.out.println(pageHtml);
    }

}
