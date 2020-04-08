package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author whj
 * @createTime 2020-02-24 23:10
 * @description
 **/
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
