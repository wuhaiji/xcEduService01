package com.xuecheng.fastdfs;

import com.xuecheng.fastdfs.util.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author whj
 * @createTime 2020-03-01 18:00
 * @description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastdfsTest {

    @Test
    public void testUpload() throws IOException, MyException {
        //配置文件加载
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        //定义trackerclient
        TrackerClient trackerClient = new TrackerClient();
        //连接tracker
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        //创建storageclient
        StorageClient storageClient = new StorageClient(trackerServer, storeStorage);
        //上传文件
        /**
         * 第一个是文件地址
         * 第二个是文件后缀名
         * 第三个是文件原信息
         */
        String[] info = storageClient.upload_file("D:\\test2.png", "png", null);
        for (String s : info) {
            System.out.println(s);
        }

    }

    @Test
    public void Upload() {
        File file = new File("d:/test.jpg");
        String str = FastDFSClient.uploadFile(file);
        FastDFSClient.getResAccessUrl(str);
    }

    @Test
    public void testDownload() throws IOException, MyException {
        //配置文件加载
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        //定义trackerclient
        TrackerClient trackerClient = new TrackerClient();
        //连接tracker
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        //创建storageclient
        StorageClient storageClient = new StorageClient(trackerServer, storeStorage);
        //下载文件
        byte[] group1s = storageClient.download_file("group1",
                "M00/00/00/rBEw5V5bn2GAGYoJAAA2TueD3MM372.png");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/testdown1.png"));
        fileOutputStream.write(group1s);
        fileOutputStream.close();
    }


}
