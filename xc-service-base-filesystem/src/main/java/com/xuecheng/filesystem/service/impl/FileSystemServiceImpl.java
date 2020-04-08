package com.xuecheng.filesystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author whj
 * @createTime 2020-03-01 22:25
 * @description
 **/
@Service
public class FileSystemServiceImpl implements FileSystemService {
    @Autowired
    FileSystemRepository fileSystemRepository;

    @Value("${xuecheng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    String charset;


    @Override
    public UploadFileResult upload(MultipartFile file, String fileTag, String businessKey, String metaData) {
        if (file == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //将文件上传到fastDfs,得到文件ID
        String fileId = this.fileUpload(file);
        if (StringUtils.isEmpty(fileId)) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        //保存文件信息
        FileSystem fileSystem = new FileSystem();
        fileSystem.setBusinesskey(businessKey);
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(fileTag);
        fileSystem.setFileName(file.getOriginalFilename());
        fileSystem.setFileType(file.getContentType());
        if (!StringUtils.isEmpty(metaData)) {
            fileSystem.setMetadata(JSON.parseObject(metaData));
        }
        FileSystem save = fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, save);
    }


    /**
     * 初始化fastDfs环境
     */
    private void initFdfsConfig(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
            //初始化文件系统出错
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }

    /**
     * 上传文件到fastdfs
     *
     * @param file 文件
     */
    private String fileUpload(MultipartFile file) {
        try {
            //配置文件加载
            System.out.println("追踪服务器:"+tracker_servers);
            System.out.println("网络超时时间:"+network_timeout_in_seconds);
            System.out.println("连接超时时间:"+connect_timeout_in_seconds);
            System.out.println("编码:"+charset);
            ClientGlobal.initByProperties("fastdfs-client.properties");
            //定义trackerclient
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storageclient
            StorageClient storageClient = new StorageClient(trackerServer, storeStorage);
            //上传文件
            byte[] bytes = file.getBytes();
            //文件扩展名
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String[] info = storageClient.upload_file(bytes, extName, null);
            return info[0] + "/" + info[1];
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        return null;
    }


}
