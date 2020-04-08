package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author whj
 * @createTime 2020-02-26 11:03
 * @description
 **/

@Api(value = "课程目录管理", tags = "课程目录管理，课程的增删改查")
public interface CategoryControllerApi {

    @ApiOperation("查询课程分类列表")
    CategoryNode findCategoryList();


}
