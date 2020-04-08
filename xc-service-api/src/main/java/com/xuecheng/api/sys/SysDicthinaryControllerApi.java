package com.xuecheng.api.sys;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author whj
 * @createTime 2020-02-29 19:58
 * @description
 **/
@Api(value = "字典", tags = "字典管理接口,字典的增删改查")
public interface SysDicthinaryControllerApi {

    /**
     * 获取数据库字典
     * @param type 类型
     * @return 字典列表
     */
    @ApiOperation(value = "数据字典查询接口")
    SysDictionary getByType(String type);

}
