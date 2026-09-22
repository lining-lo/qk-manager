package com.qk.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Slf4j
@Component
public class AliyunOSSOperator {

    //private static final String ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";
    //private static final String BUCKET_NAME = "qk-parent-zz";
    //private static final String REGION = "cn-hangzhou";

    //需求：读取yml文件中的配置信息赋值给成员变量
    //方式一：使用@Value("${key}")将属于一个一个注入给成员变量
    //@Value("${aliyun.oss.endpoint}")
    //private String endpoint;
    //@Value("${aliyun.oss.bucketName}")
    //private String bucketName;
    //@Value("${aliyun.oss.region}")
    //private String region;

    //方式二：使用@ConfigurationProperties(prefix = "aliyun.oss")将配置信息按照前缀批量注入到一个对象中
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    public String upload(byte[] content, String originalFilename) throws Exception {
        //从aliyunOSSProperties中获取配置信息
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();

        // 获取原始文件名的后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一文件名
        String objectName = UUID.randomUUID().toString() + suffixName;
        // 创建OSSClient实例
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = null;
        try {
            ossClient = OSSClientBuilder.create()
                    .endpoint(endpoint)
                    .credentialsProvider(new EnvironmentVariableCredentialsProvider())
                    .clientConfiguration(clientBuilderConfiguration)
                    .region(region)
                    .build();
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content));
            // 上传文件
            ossClient.putObject(putObjectRequest);
            // 返回文件访问URL
            return "https://" + bucketName + "." + endpoint.substring(8) + "/" + objectName;
        } catch (Exception e) {
            log.error("Caught an OSSException: " + e.getMessage());
            throw e;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}