package com.clay.route53sslmonitor;

import com.amazonaws.services.route53.AmazonRoute53;
import com.clay.route53sslmonitor.aws.Route53Service;
import com.clay.route53sslmonitor.aws.Route53ServiceImpl;
import com.clay.route53sslmonitor.config.AWSConfig;
import com.clay.route53sslmonitor.util.CustomAWSClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Route53SslMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(Route53SslMonitorApplication.class, args);
	}

	@Bean
	public AWSConfig awsConfig() {
		return new AWSConfig();
	}

	@Bean
	public AmazonRoute53 amazonRoute53() throws Exception {
		AWSConfig awsConfig = awsConfig();
		return new CustomAWSClient().getRoute53Client(
				awsConfig.getAccessKey(),
				awsConfig.getSecretKey(),
				awsConfig.getRegion()
		);
	}

}
