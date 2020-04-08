package com.xuecheng.filesystem.controller;

import com.xuecheng.api.sys.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author whj
 * @createTime 2020-03-01 22:21
 * @description
 **/
@RestController
@RequestMapping("/filesystem")
public class FileSystemControler implements FileSystemControllerApi {

    @Autowired
    private FileSystemService fileSystemService;

    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(@RequestParam MultipartFile file,
                                   @RequestParam String fileTag, String businessKey, String metaData) {
        return fileSystemService.upload(file, fileTag, businessKey, metaData);
    }
}
