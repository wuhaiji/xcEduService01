package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author whj
 * @createTime 2020-03-01 22:23
 * @description
 **/
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {

}
