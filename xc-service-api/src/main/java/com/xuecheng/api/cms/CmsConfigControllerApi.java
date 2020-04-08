package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * createTime 2020.02.06 16:45
 */
@Api(value="cms配置管理接口",tags = "cms配置管理接口，提供页面配置的增删改查")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS配置信息")
     ResponseResult getModel(String id);
}
