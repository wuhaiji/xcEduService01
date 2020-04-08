package com.xuecheng.manage_cms_client.Service;

import com.xuecheng.framework.domain.cms.CmsPage;

public interface PageService {
    /**
     * 将页面html保存到页面物理路径
     * @param pageId
     * @return
     */
    void savePageToServerPath(String pageId) ;

    /**
     * 通过pageId查询页面
     * @param pageId 页面ID
     * @return 页面
     */
    CmsPage findCmsPageById(String pageId);


}
