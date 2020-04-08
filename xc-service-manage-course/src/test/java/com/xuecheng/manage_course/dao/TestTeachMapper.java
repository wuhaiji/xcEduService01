package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author whj
 * @createTime 2020-02-26 14:51
 * @description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTeachMapper {

    @Autowired
    TeachPlanMapper teachPlanMapper;
    @Test
    public void teachPlanMapper(){
        TeachplanNode teachPlanList = teachPlanMapper.findTeachPlanList("4028e581617f945f01617f9dabc40000");
        System.out.println(teachPlanList);

    }
}
