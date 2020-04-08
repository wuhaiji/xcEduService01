package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.model.response.CommonResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author whj
 * @createTime 2020-02-10 18:18
 * @description
 **/
@Service
public class CmsConfigServiceImpl implements CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;
    @Override
    public ResponseResult getById(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if(optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            return CommonResponseResult.Ok(cmsConfig);
        }
        return new ResponseResult(CmsCode.CMS_COURSE_PAGENOTEXIST);
    }
}
