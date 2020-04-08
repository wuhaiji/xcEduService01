package com.xuecheng.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author whj
 * @createTime 2020-03-01 18:03
 * @description
 **/
//fastdfs注解
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)

@SpringBootApplication
public class FastdfsApp {

    public static void main(String[] args) {
        SpringApplication.run(FastdfsApp.class, args);
    }
}
