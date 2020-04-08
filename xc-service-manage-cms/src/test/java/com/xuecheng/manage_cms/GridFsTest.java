package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @author whj
 * @createTime 2020-02-10 20:24
 * @description
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * gridFs存文件
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testGridFsStore() throws FileNotFoundException {
        File file = new File("D:/course.ftl");
        InputStream inputStream = new FileInputStream(file);
        ObjectId store = gridFsTemplate.store(inputStream, "course.ftl");
        System.out.println(store);
    }

    /**
     * 取文件
     */
    @Test
    public void testGridFsGet() throws IOException {
        String fileId = "5e41561b08a48b278814c24d";
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
       //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        String s = IOUtils.toString(gridFsResource.getInputStream(),"utf-8") ;
        IOUtils.write(s,new FileOutputStream("D:/test3.html"),"uft-8");
    }
}
