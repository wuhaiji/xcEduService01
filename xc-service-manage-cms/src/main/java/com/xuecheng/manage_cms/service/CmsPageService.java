package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CmsPageService {
    QueryResponseResult findList(int page, int size, QueryPageRequest QueryPageRequest);

    CmsPageResult add(CmsPage cmsPage);

    CmsPageResult edit(CmsPage cmsPage, String id);

    CmsPageResult getById(String id);

    CmsPageResult delete(String id);

    String getPageHtml(String pageId) ;

    ResponseResult post(String pageId);

    CmsPageResult save(CmsPage cmsPage);

    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
