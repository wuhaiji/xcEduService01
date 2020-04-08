package com.xuecheng.manage_cms_client.Service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.Service.PageService;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author whj
 * @createTime 2020-02-24 23:16
 * @description
 **/
@Slf4j
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    @Override
    public void savePageToServerPath(String pageId) {
        //得到html id,从cmsPage中得到HtmlFileID
        CmsPage cmsPageById = this.findCmsPageById(pageId);
        if (cmsPageById == null) {
            ExceptionCast.cast(CmsCode.CAMS_PAGE_NONSEXIST);
        }
        String htmlFileId = cmsPageById.getHtmlFileId();
        //从gridFs取出文件
        InputStream inputStream = this.getHtmlFileById(htmlFileId);
        if (inputStream == null) {
            log.info("getHtmlFileById InputSteam is null,htmlFileId:{}", htmlFileId);
            return;
        }
        //得到站点的信息
        String siteId = cmsPageById.getSiteId();
        CmsSite site = this.getSiteById(siteId);

        //拼接物理地址:站点物理地址+页面物理地址+页面名称
        String path = site.getSitePhysicalPath()+ cmsPageById.getPagePhysicalPath() + cmsPageById.getPageName();
        //保存文件到物理地址
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(path));
            IOUtils.copy(inputStream, new FileOutputStream(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public CmsSite getSiteById(String siteId) {
        Optional<CmsSite> byId = cmsSiteRepository.findById(siteId);
        return byId.orElse(null);
    }

    public InputStream getHtmlFileById(String htmlFileId) {
        //文件对象
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        //打开下载流
        assert gridFSFile != null;
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //定义GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        InputStream inputStream = null;
        try {
            inputStream = gridFsResource.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    //通过id查询页面
    @Override
    public CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        return byId.orElse(null);
    }
}
