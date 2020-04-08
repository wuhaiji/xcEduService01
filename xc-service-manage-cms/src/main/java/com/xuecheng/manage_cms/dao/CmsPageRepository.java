package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    CmsPage findByPageName(String pageName);

    /**
     * 根据页面名称、站点ID、页面webpath查询
     */
    CmsPage findByPageNameAndAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);
    /**
     * 根据页面名称、站点ID、页面webpath查询
     */
    List<CmsPage> findAllByPageNameAndAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);

}
