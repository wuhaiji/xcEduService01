package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;

/**
 * @author whj
 */
public interface SysDicthinarySerivce {

    /**
     * 获取字典数据通过类型
     * @param type 字典类型
     * @return 字典列表
     */
    SysDictionary getByType(String type);
}
