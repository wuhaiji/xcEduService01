package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDicthinaryRepository;
import com.xuecheng.manage_cms.service.SysDicthinarySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author whj
 * @createTime 2020-02-29 20:24
 * @description
 **/
@Service
public class SysDicthinarySerivceImpl implements SysDicthinarySerivce {
    @Autowired
    private SysDicthinaryRepository sysDicthinaryRepository;
    @Override
    public SysDictionary getByType(String type) {
        return sysDicthinaryRepository.findByDType(type);
    }
}
