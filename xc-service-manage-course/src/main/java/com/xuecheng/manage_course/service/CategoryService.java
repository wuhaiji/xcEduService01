package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;

/**
 * @author whj
 * @createTime 2020-02-29 21:37
 * @description
 **/
public interface CategoryService {
    /**
     * 查询课程类别列表
     * @return 类别节点
     */
    CategoryNode findCategoryList();
}
