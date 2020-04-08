package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author whj
 * @createTime 2020-02-26 11:03
 * @description
 **/

@Api(value = "课程管理", tags = "课程管理，课程的增删改查")
public interface CourseControllerApi {
    /**
     * 课程计划查询
     *
     * @param courseId 课程ID
     * @return 响应对象
     */
    @ApiOperation("课程计划查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path")
    })
    TeachplanNode findTeachPlanList(String courseId);

    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划模型对象
     * @return 响应对象
     */
    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(@RequestBody Teachplan teachplan);

    /**
     * 分页查询课程列表
     *
     * @param page              页码
     * @param size              一页数据个数
     * @param courseListRequest 其他查询参数
     * @return 响应对象
     */
    @ApiOperation("分页查询课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页记录", required = true, paramType = "path"),
    })
    QueryResponseResult<CourseInfo> findCourseList(Integer page, Integer size, CourseListRequest courseListRequest);

    /**
     * 添加课程
     *
     * @param courseBase entity
     * @return
     */
    @ApiOperation("分页查询课程列表")
    ResponseResult addcoursebase(@RequestBody CourseBase courseBase);

    /**
     * 查询课程基础信息
     *
     * @param courseId
     * @return
     * @throws RuntimeException
     */
    @ApiOperation("获取课程基础信息")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path")
    CourseBase getCourseBaseById(String courseId);

    /**
     * 获取课程营销信息
     *
     * @param courseId
     * @return
     */
    @ApiOperation("获取课程营销信息")
    CourseMarket getCourseMarketById(String courseId);


    /**
     * 更新课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @ApiOperation("更新课程营销信息")
    @ApiImplicitParam(name = "id", value = "课程营销信息id", required = true, paramType = "path")
    ResponseResult updateCourseMarket(String id, @RequestBody CourseMarket courseMarket);

    /**
     * 更新课程营销信息
     *
     * @param courseId
     * @param pic
     * @return
     */
    @ApiOperation("添加课程图片")
    ResponseResult addCoursePic(String courseId, String pic);

    /**
     * 获取课程的图片列表
     *
     * @param courseId
     * @return
     */
    @ApiOperation("获取课程的图片列表")
    CoursePic findCoursePic(String courseId);

    /**
     * 删除课程图片
     *
     * @param courseId
     * @return
     */
    @ApiOperation("删除课程图片")
    ResponseResult deleteCoursePic(String courseId);

    /**
     * 课程预览
     * @param id 课程ID
     * @return CourseView
     */
    @ApiOperation("课程预览数据源")
    CourseView courseViewModel(String id);

    /**
     * 课程预览
     * @param id 课程ID
     * @return CourseView
     */
    @ApiOperation("课程预览")
    CoursePublishResult preview(String id);
}
