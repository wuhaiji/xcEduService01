package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator.
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary, String> {
    /**
     * 通过类型查询字典
     * @param dType 类型
     * @return 字典列表
     */
    SysDictionary findByDType(String dType);

}
