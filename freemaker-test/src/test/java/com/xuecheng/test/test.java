package com.xuecheng.test;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javafx.application.Application;
import org.junit.Test;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author whj
 * @createTime 2020-02-09 21:22
 * @description
 **/
@SpringBootTest(classes = Application.class)
public class test {
    /**
     * 基于ftl生成html文件
     */
    @Test
    public void toGenerateHtml() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //定义模板路径
        String path = this.getClass().getClassLoader().getResource("./templates").getPath();
        configuration.setDirectoryForTemplateLoading(new File(path));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模板
        Template template = configuration.getTemplate("test1.ftl");
        Map map = getMap();
        //静态化
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        IOUtil.writeText(html, new File("D:/test.html"));
    }


    /**
     * 基于字符串模版生成html
     *
     * @return
     */
    @Test
    public void tocreateHtmlByString() throws IOException, TemplateException {
        String templateString = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Hello World!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                " 姓名：${(stuMap.stu1.name)!''}<br/>\n"+
                "</body>\n" +
                "</html>";
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateString);
        //设置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("template", "uft-8");

        Map map = getMap();
        //静态化
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        IOUtil.writeText(html, new File("D:/test.html"));
    }

    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        //向数据模型放数据
        map.put("name", "黑马程序员");
//        Student stu1 = new Student();
//        stu1.setName("小明");
//        stu1.setAge(18);
//        stu1.setMoney(1000.86f);
//        stu1.setBirthday(new Date());
//        Student stu2 = new Student();
//        stu2.setName("小红");
//        stu2.setMoney(200.1f);
//        stu2.setAge(19);
//        stu2.setBirthday(new Date());
//        List<Student> friends = new ArrayList<>();
//        friends.add(stu1);
//        stu2.setFriends(friends);
//        stu2.setBestFriend(stu1);
//        List<Student> stus = new ArrayList<>();
//        stus.add(stu1);
//        stus.add(stu2);
//        //向数据模型放数据
//        map.put("stus", stus);
//        //准备map数据
//        HashMap<String, Student> stuMap = new HashMap<>();
//        stuMap.put("stu1",stu1);
//        stuMap.put("stu2", stu2);
//        //向数据模型放数据
//        map.put("stu1", stu1);
//        //向数据模型放数据
//        map.put("stuMap", stuMap);
        map.put("num", 1852369);
        return map;
    }
}
