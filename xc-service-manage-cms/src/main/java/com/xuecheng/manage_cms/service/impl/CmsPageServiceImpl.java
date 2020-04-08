package com.xuecheng.manage_cms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @author whj
 * @createTime 2020-02-07 11:32
 * @description
 **/
@Service
@Slf4j
public class CmsPageServiceImpl implements CmsPageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsTemplateRepository CmsTemplateRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public CmsPageServiceImpl(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

    /**
     * 页面分页查询方法
     *
     * @param page             页码，从1开始计数
     * @param size             每页记录数
     * @param queryPageRequest 条件查询对象
     * @return
     */
    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;//为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        //构造条件对象
        CmsPage cmsPage = new CmsPage();
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setTotal(all.getTotalElements());
        cmsPageQueryResult.setList(all.getContent());
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
        return queryResponseResult;
    }

    /**
     * 添加page
     *
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        // 从数据库验证页面名称、站点Id、页面webpath唯一性
        CmsPage cmsPage1 = cmsPageRepository
                .findByPageNameAndAndSiteIdAndPageWebPath(
                        cmsPage.getPageName(),
                        cmsPage.getSiteId(),
                        cmsPage.getPageWebPath()
                );
        if (cmsPage1 != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 编辑page
     *
     * @param cmsPage
     * @param id
     * @return
     */
    @Override
    public CmsPageResult edit(CmsPage cmsPage, String id) {
        //检查是否存在
        Optional<CmsPage> cmsPage2 = cmsPageRepository.findById(id);
        if (!cmsPage2.isPresent()) {
            return new CmsPageResult(CmsCode.CMS_COURSE_PAGENOTEXIST, null);
        }

        //判断是否修改了名称，站点，网络地址这三项不能同时重复的信息
        boolean equals1 = cmsPage.getPageName().equals(cmsPage2.get().getPageName());
        boolean equals2 = cmsPage.getPageWebPath().equals(cmsPage2.get().getPageWebPath());
        boolean equals3 = cmsPage.getSiteId().equals(cmsPage2.get().getSiteId());
        if (!(equals1 && equals2 && equals3)) {
            //检查关键数据是否重复
            CmsPage cmsPage1 = cmsPageRepository
                    .findByPageNameAndAndSiteIdAndPageWebPath(
                            cmsPage.getPageName(),
                            cmsPage.getSiteId(),
                            cmsPage.getPageWebPath()
                    );
            if (cmsPage1 != null) {
                return new CmsPageResult(CmsCode.CMS_ADDPAGE_EXISTSNAME, null);

            }
        }

        //保存
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 获取page通过id
     *
     * @param id
     * @return
     */
    @Override
    public CmsPageResult getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CmsCode.CMS_COURSE_PAGENOTEXIST, null);
    }

    @Override
    public CmsPageResult delete(String id) {
        cmsPageRepository.deleteById(id);
        return new CmsPageResult(CommonCode.SUCCESS, null);
    }

    /**
     * 页面静态化的方法
     * <p>
     * 1、静态化程序获取页面的DataUrl
     * 2、静态化程序远程请求DataUrl获取数据模型。
     * 3、静态化程序获取页面的模板信息
     * 4、执行页面静态化
     */
    @Override
    public String getPageHtml(String pageId) {
        //获取数据模型
        Map modelById = this.getModelById(pageId);
        if (modelById == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        Map data;
        if (modelById.containsKey("success") && modelById.containsKey("code") && modelById.containsKey("message")) {
            boolean success = (boolean) modelById.get("success");
            if (CommonCode.SUCCESS.success() != success) {
                ExceptionCast.cast(CommonCode.FAIL);
            }
            data = (Map) modelById.get("data");
        }
        data = modelById;
        //获取模版
        String templateString = this.getTemplateByPageId(pageId);
        return genrateHtml(templateString, data);
    }


    //执行静态化
    private String genrateHtml(String templateString, Map data) {
        //静态化
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        //设置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        String html = null;
        try {
            Template template = configuration.getTemplate("template", "uft-8");
            //静态化
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return html;
    }

    //获取模版
    private String getTemplateByPageId(String pageId) {
        //获取页面
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CAMS_PAGE_NONSEXIST);
        }
        CmsPage cmsPage = optional.get();
        //获取模版id
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        Optional<CmsTemplate> optional1 = CmsTemplateRepository.findById(templateId);

        String s = "";
        if (optional1.isPresent()) {
            CmsTemplate template = optional1.get();
            String templateFileId = template.getTemplateFileId();
            GridFSFile gridFSFile =
                    gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            assert gridFSFile != null;
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            try {
                s = IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return s;
    }


    /**
     * 获取数据模型
     */
    private Map getModelById(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CAMS_PAGE_NONSEXIST);
        }
        CmsPage cmsPage = optional.get();
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();
    }


    /**
     * 发布页面
     *
     * @param pageId
     * @return
     */
    @Override
    public ResponseResult post(String pageId) {
        //执行页面静态化
        String pageHtml = this.getPageHtml(pageId);
        if (StringUtils.isEmpty(pageHtml)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        //将页面静态化文件存到gridFs
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CAMS_PAGE_NONSEXIST);
        }
        //发布Rabbitmq消息
        this.sendMsg(pageId);
        return ResponseResult.SUCCESS();
    }

    @Override
    public CmsPageResult save(CmsPage cmsPage) {
        CmsPage cmsPage1 = cmsPageRepository
                .findByPageNameAndAndSiteIdAndPageWebPath(
                cmsPage.getPageName(),
                cmsPage.getSiteId(),
                cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            String pageId = cmsPage1.getPageId();
            cmsPage.setPageId(pageId);
        }
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    @Override
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //添加页面
        CmsPageResult save = this.save(cmsPage);
        CmsPage cmsPage1 = save.getCmsPage();
        if(save.isSuccess()){
            return new CmsPostPageResult(CommonCode.FAIL,null);
        }
        //要发布的页面ID
        String pageId=cmsPage1.getPageId();
        //发布页面
        //发布页面
        ResponseResult responseResult = this.post(pageId);
        if(!responseResult.isSuccess()){
            return new CmsPostPageResult(CommonCode.FAIL,null);
        }
        //得到页面的url
        //页面url=站点域名+站点webpath+页面webpath+页面名称
        //站点id
        String siteId = cmsPage1.getSiteId();
        //查询站点信息
        Optional<CmsSite> siteById = cmsSiteRepository.findById(siteId);
        if(!siteById.isPresent()){
           ExceptionCast.cast(CmsCode.CMS_SITE_NOTEXIST);
        }
        CmsSite cmsSite =siteById.get();
        //站点域名
        String siteDomain = cmsSite.getSiteDomain();
        //站点web路径
        String siteWebPath = cmsSite.getSiteWebPath();
        //页面web路径
        String pageWebPath = cmsPage1.getPageWebPath();
        //页面名称
        String pageName = cmsPage1.getPageName();
        //页面的web访问地址
        String pageUrl = siteDomain+siteWebPath+pageWebPath+pageName;
        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);
    }

    //发布Rabbitmq消息
    private void sendMsg(String pageId) {
        CmsPage cmsPage = this.getById(pageId).getCmsPage();
        JSONObject msg = new JSONObject();
        msg.put("pageId", pageId);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, cmsPage.getSiteId(), msg.toJSONString());
    }

    //将页面静态化文件存到gridFs
    private CmsPage saveHtml(String pageId, String htmlContent) {
        //查询页面
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CAMS_PAGE_NONSEXIST);
        }
        CmsPage cmsPage = optional.get();
        //存储之前先删除
        String htmlFileId = cmsPage.getHtmlFileId();
        if (StringUtils.isNotEmpty(htmlFileId)) {
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        ObjectId store = null;
        //将html保存到gridFs
        InputStream inputStream = null;
        try {
            inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            store = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将html文件id更新到cmsPage中
        cmsPage.setHtmlFileId(store.toHexString());
        return cmsPageRepository.save(cmsPage);
    }

}
