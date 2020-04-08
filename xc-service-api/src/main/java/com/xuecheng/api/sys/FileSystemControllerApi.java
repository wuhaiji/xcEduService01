package com.xuecheng.api.sys;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author whj
 * @createTime 2020-02-29 19:58
 * @description
 **/
@Api(value = "文件管理系统", tags = "文件管理系统,文件的增删改查")
public interface FileSystemControllerApi {
    /**
     * 上传文件
     *
     * @param multipartFile 上传的文件
     * @param fileTag       文件所属模块标记
     * @param businessKey   业务key
     * @param metaData      文件元信息
     * @return 响应
     */
    @ApiOperation(value = "上传文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileTag", value = "文件所属模块标记", required = true, paramType = "form"),
            @ApiImplicitParam(name = "businessKey", value = "业务key", required = false, paramType = "form"),
            @ApiImplicitParam(name = "metaData", value = "文件元信息", required = false, paramType = "form")
    })
    UploadFileResult upload(MultipartFile multipartFile, String fileTag, String businessKey, String metaData);

}
