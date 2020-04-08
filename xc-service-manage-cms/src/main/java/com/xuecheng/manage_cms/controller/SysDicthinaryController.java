package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.sys.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDicthinarySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author whj
 * @createTime 2020-02-29 20:20
 * @description 数据库字典管理
 **/
@RestController
@RequestMapping("/sys/")
public class SysDicthinaryController implements SysDicthinaryControllerApi {

    @Autowired

    private SysDicthinarySerivce sysDicthinarySerivce;

    @Override
    @GetMapping("/dictionary/get/{type}")
    public SysDictionary getByType(@PathVariable String type) {
        return sysDicthinarySerivce.getByType(type);
    }
}
