package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author whj
 * @createTime 2020-02-24 23:10
 * @description
 **/
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

}
