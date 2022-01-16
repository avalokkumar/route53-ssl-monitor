package com.clay.route53sslmonitor.config;

import com.clay.route53sslmonitor.model.AWSConfigType;
import com.clay.route53sslmonitor.model.RegionCode;
import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "aws.prop")
public class AWSConfig {

    private AWSConfigType type;
    private String hostedZoneId;
    private String accessKey;
    private String secretKey;
    private String roleArn;
    private String region;
}