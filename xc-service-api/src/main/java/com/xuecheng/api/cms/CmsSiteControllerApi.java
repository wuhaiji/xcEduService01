package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * createTime 2020.02.06 16:45
 * @author Administrator
 */
@Api(value="cms站点管理接口",tags = "cms站点管理接口，提供站点的增删改查")
public interface CmsSiteControllerApi {
    @ApiOperation("查询site下拉框列表")
    QueryResponseResult findList();
}
