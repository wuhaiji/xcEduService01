package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author whj
 * @createTime 2020-02-06 22:09
 * @description contoller
 **/
@RestController
@RequestMapping("/cms/page")
public class CmsPageController extends BaseController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;


    @Override

    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable int page, @PathVariable int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPageResult getById(@PathVariable String id) {
        return cmsPageService.getById(id);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.edit(cmsPage, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public CmsPageResult delete(@PathVariable String id) {
        return cmsPageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult postPage(@PathVariable("pageId") String pageId) {
        return cmsPageService.post(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }

    @Override
    @PostMapping("/publish")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return cmsPageService.postPageQuick(cmsPage);
    }


}
