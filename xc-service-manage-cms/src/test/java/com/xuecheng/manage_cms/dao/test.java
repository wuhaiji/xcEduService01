package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author whj
 * @createTime 2020-02-06 23:14
 * @description test
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteService cmsSiteService;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void findAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    /**
     * 分页查询
     */
    @Test
    public void findPage() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    /**
     * 更新文档
     */
    @Test
    public void toUpdate() {
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById("5e3cd69972158d7de4927478");
        if (optionalCmsPage.isPresent()) {
            CmsPage cmsPage = optionalCmsPage.get();
            cmsPage.setPageAliase("test002");
            CmsPage save = cmsPageRepository.save(cmsPage);
        }
    }

    /**
     * 自定义方法查询
     */
    @Test
    public void findByPageName() {
        CmsPage byPageName = cmsPageRepository.findByPageName("test.html");
        System.out.println(byPageName);
    }

    /**
     * 自定义条件查询
     */
    @Test
    public void findAllByExample() {
        //分页信息
        Pageable pageable = PageRequest.of(0, 3);
        //条件对象
        CmsPage cmsPage = new CmsPage();
        //设置站点ID
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //设置模板ID
//        cmsPage.setTemplateId("5a962c16b00ffc514038fafd");
        cmsPage.setPageAliase("轮播");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    /**
     * 站点列表查询
     */
    @Test
    public void findsiteList() {
        QueryResponseResult list = cmsSiteService.findList();
        System.out.println(list.getQueryResult());
    }

    /**
     * 远程请求cmsConfig接口
     */
    @Test
    public void toRestTemplate() {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://127.0.0.1:31001/cms/config/get/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        System.out.println(body);
    }
}
