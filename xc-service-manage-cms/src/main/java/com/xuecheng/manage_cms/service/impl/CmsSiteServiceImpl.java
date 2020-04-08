package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.service.CmsSiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author whj
 * @createTime 2020-02-07 11:32
 * @description
 **/
@Service
@Slf4j
public class CmsSiteServiceImpl implements CmsSiteService {

   @Autowired
   private CmsSiteRepository cmsSiteRepository;

    /**
     * 查询所有站点信息
     *
     * @return
     */
    @Override
    public QueryResponseResult findList() {
        QueryResult<CmsSite> cmsSiteQueryResult = new QueryResult<>();
        List<CmsSite> all = cmsSiteRepository.findAll();
        cmsSiteQueryResult.setList(all);
        cmsSiteQueryResult.setTotal(-1);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, cmsSiteQueryResult);
        return queryResponseResult;
    }
}
