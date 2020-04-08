package com.xuecheng.manage_course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author whj
 * @createTime 2020-02-26 12:37
 * @description
 **/
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    TeachPlanMapper teachPlanMapper;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CourseMarketRepository courseMarketRepository;

    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CmsPageClient cmsPageClient;


    @Value("${course‐publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course‐publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course‐publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course‐publish.siteId}")
    private String publish_siteId;
    @Value("${course‐publish.templateId}")
    private String publish_templateId;
    @Value("${course‐publish.previewUrl}")
    private String previewUrl;

    @Override
    public TeachplanNode findTeachPlanList(String courseId) {
        return teachPlanMapper.findTeachPlanList(courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //参数判断
        if (teachplan == null
                || StringUtils.isEmpty(teachplan.getCourseid())
                || StringUtils.isEmpty(teachplan.getPname())
        ) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //取出课程id
        String courseId = teachplan.getCourseid();
        //处理parentId
        String parentId = teachplan.getParentid();

        //查询课程的根节点，如果根节点id为空，则通过课程id取查询根节点id,如果查询不到自动添加根节点并返回ID
        if (StringUtils.isEmpty(parentId)) {
            parentId = this.getTeachPlanRoot(courseId);
            //补充parentId
            teachplan.setParentid(parentId);
            //如果parentId为空,添加的是二级节点
            teachplan.setGrade("2");
            teachplanRepository.save(teachplan);
            return ResponseResult.SUCCESS();
        }
        //如果parentId不为空，添加的是三级节点
        teachplan.setGrade("3");
        //添加课程计划
        teachplanRepository.save(teachplan);
        return ResponseResult.SUCCESS();

    }

    @Override
    public QueryResponseResult<CourseInfo> findCourseList(Integer page, Integer size, CourseListRequest courseListRequest) {
        //查询
        PageHelper.startPage(page, size);
        Page<CourseInfo> pageList = courseMapper.findCourseList(courseListRequest);
        //封装
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(pageList.getResult());
        courseInfoQueryResult.setTotal(pageList.getTotal());
        return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS, courseInfoQueryResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addcoursebase(CourseBase courseBase) {
        CourseBase save = courseBaseRepository.save(courseBase);
        return new CommonResponseResult(CommonCode.SUCCESS, save);
    }

    @Override
    public CourseBase getCourseBaseById(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseBase> byId = courseBaseRepository.findById(courseId);
        if (!byId.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_BASE_COURSENOTEXIST);
        }
        return byId.get();
    }

    @Override
    public CourseMarket getCourseMarketById(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseMarket> byId = courseMarketRepository.findById(courseId);
        return byId.orElseGet(CourseMarket::new);
    }

    @Override
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        //首先查询课程营销信息
        if (StringUtils.isEmpty(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        //查询不到则添加营销信息
        courseMarket.setId(id);
        CourseMarket save = courseMarketRepository.save(courseMarket);
        return ResponseResult.SUCCESS();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addCoursePic(String courseId, String pic) {
        if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(pic)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //先查询一下,如果存在就更新
        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        CoursePic save = coursePicRepository.save(coursePic);
        return ResponseResult.SUCCESS();
    }

    @Override
    public CoursePic findCoursePic(String courseId) {
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        return byId.orElseGet(CoursePic::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteCoursePic(String courseId) {
        coursePicRepository.deleteById(courseId);
        return ResponseResult.SUCCESS();
    }

    @Override
    public CourseView courseview(String id) {
        CourseView courseView = new CourseView();
        //获取基础信息
        Optional<CourseBase> courseBaseById = courseBaseRepository.findById(id);
        courseBaseById.ifPresent(courseView::setCourseBase);
        //图片信息
        Optional<CoursePic> coursePicById = coursePicRepository.findById(id);
        coursePicById.ifPresent(courseView::setCoursePic);
        //营销信息
        Optional<CourseMarket> courseMarketById = courseMarketRepository.findById(id);
        courseMarketById.ifPresent(courseView::setCourseMarket);
        //教学计划信息
        TeachplanNode teachPlanList = teachPlanMapper.findTeachPlanList(id);
        courseView.setTeachplanNode(teachPlanList);
        return courseView;
    }

    @Override
    public CoursePublishResult preview(String id) {
        Optional<CourseBase> byId = courseBaseRepository.findById(id);
        if(!byId.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_BASE_COURSENOTEXIST);
        }
        CmsPage cmsPage = new CmsPage();
        CourseBase courseBase = byId.get();
        //发布课程预览页面
        //站点
        cmsPage.setSiteId(publish_siteId);
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        //页面名称
        cmsPage.setPageName(id+".html");
        //页面别名
        cmsPage.setPageAliase(courseBase.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);

        //请求cms添加页面
        CmsPageResult save = cmsPageClient.save(cmsPage);

        //拼装返回 url
        String pageId = save.getCmsPage().getPageId();
        String pageUrl = previewUrl+pageId;
        //返回CoursePublishResult
        return  new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }


    /**
     * 查询课程的根节点，查询不到自动添加根节点
     *
     * @param courseId 课程ID
     * @return 根节点的ID
     */
    private String getTeachPlanRoot(String courseId) {
        Optional<CourseBase> byId = courseBaseRepository.findById(courseId);
        if (!byId.isPresent()) {
            return null;
        }
        //取出该课程的根节点
        CourseBase courseBase = byId.get();
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachplanList == null || teachplanList.size() == 0) {
            Teachplan entity = new Teachplan();
            entity.setCourseid(courseId);
            entity.setParentid("0");
            entity.setGrade("1");
            entity.setPname(courseBase.getName());
            entity.setStatus("0");
            Teachplan save = teachplanRepository.save(entity);
            return save.getId();
        }

        return teachplanList.get(0).getId();
    }
}
