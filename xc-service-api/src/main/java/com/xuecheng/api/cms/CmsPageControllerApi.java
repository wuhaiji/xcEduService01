package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * createTime 2020.02.06 16:45
 */
@Api(value = "cms页面管理接口", tags = "cms页面管理接口，提供页面的增删改查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页记录", required = true, paramType = "path")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("新增页面")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("查询单个页面信息")
    CmsPageResult getById(String id);

    @ApiOperation("修改页面")
    CmsPageResult edit(String id, CmsPage cmsPage);

    @ApiOperation("删除页面")
    CmsPageResult delete(String id);

    @ApiOperation("发布页面")
    ResponseResult postPage(String pageId);

    /**
     * 课程管理添加课程页面，用做预览，如果存在相同页面就更新页面
     *
     * @param cmsPage 页面信息
     * @return 响应信息包含课程ID
     */
    @ApiOperation("保存页面")
    CmsPageResult save(CmsPage cmsPage);

    /**
     * 意一键发布页面
     * @param cmsPage 页面信息
     * @return return
     */
    @ApiOperation("一键发布页面")
     CmsPostPageResult postPageQuick(CmsPage cmsPage);

}
