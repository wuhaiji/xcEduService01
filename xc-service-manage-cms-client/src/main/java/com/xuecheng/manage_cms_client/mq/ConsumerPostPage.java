package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.Service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author whj
 * @createTime 2020-02-25 09:22
 * @description 监听队列
 **/
@Component
@Slf4j
public class ConsumerPostPage {

    @Autowired
    PageService pageService;


    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){
        JSONObject jsonObject = JSON.parseObject(msg);
        String pageId = (String) jsonObject.get("pageId");
        CmsPage page = pageService.findCmsPageById(pageId);

        //校验id是否合法
        if(page==null){
            log.error("receive postPage msg,cmsPage is null,pageId:{}",pageId);
            return;
        }

        pageService.savePageToServerPath(pageId);
    }

}
