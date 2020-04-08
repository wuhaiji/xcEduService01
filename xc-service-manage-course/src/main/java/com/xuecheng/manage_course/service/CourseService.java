package com.xuecheng.manage_course.service;

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

public interface CourseService {
    /**
     * 课程计划查询
     *
     * @param courseId
     * @return
     */
    TeachplanNode findTeachPlanList(String courseId);


    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划
     * @return
     */
    ResponseResult addTeachplan(Teachplan teachplan);

    /**
     * 分页查询课程列表
     *
     * @param page              页码
     * @param size              一页数据个数
     * @param courseListRequest 其他查询参数
     * @return 响应对象
     */
    QueryResponseResult<CourseInfo> findCourseList(Integer page, Integer size, CourseListRequest courseListRequest);

    /**
     * 添加课程基础信息
     * @param courseBase
     * @return
     */
    ResponseResult addcoursebase(CourseBase courseBase);

    /**
     * 获取课程基础信息
     * @param courseId
     * @return
     */
    CourseBase getCourseBaseById(String courseId);

    /**
     * 获取课程营销信息
     * @param courseId
     * @return
     */
    CourseMarket getCourseMarketById(String courseId);

    /**
     * 更新课程营销信息
     * @param id
     * @param courseMarket
     * @return
     */
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

    /**
     * 添加课程图片信息
     * @param courseId
     * @param pic
     * @return
     */
    ResponseResult addCoursePic(String courseId, String pic);

    /**
     * 查询课程图片列表
     * @param courseId
     * @return
     */
    CoursePic findCoursePic(String courseId);

    /**
     * 删除课程图片
     * @param courseId
     * @return
     */
    ResponseResult deleteCoursePic(String courseId);

    /**
     * 课程页面数据模型
     * @param id
     * @return
     */
    CourseView courseview(String id);

    /**
     * 预览页面
     * @param id
     * @return
     */
    CoursePublishResult preview(String id);
}
