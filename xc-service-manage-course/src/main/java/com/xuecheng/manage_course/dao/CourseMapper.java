package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {


   /**
    * 根据id查询课程
    * @param id
    * @return
    */
   CourseBase findCourseBaseById(String id);

   /**
    * 分页查询课程
    * @return 分页对象
    */
   Page<CourseBase> findCourseBase();

   /**
    * 查询我的课程列表
    * @param courseListRequest 查询参数
    * @return
    */
   Page<CourseInfo> findCourseList(CourseListRequest courseListRequest);

}
