package com.xuecheng.filesystem.service;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author whj
 */
public interface FileSystemService {

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileTag 标识该文件是属于哪个子系统
     * @param businessKey 业务key
     * @param metaData 元数据
     * @return 响应
     */
    UploadFileResult upload(MultipartFile file, String fileTag, String businessKey, String metaData);
}
