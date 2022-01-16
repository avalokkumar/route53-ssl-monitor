package com.clay.route53sslmonitor.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClientBuilder;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53ClientBuilder;

public class CustomAWSClient {

    public AmazonEC2 getEC2Client(String accessKey, String secretKey, String region) throws Exception {

        AWSCredentialsProvider provider;
        if (accessKey != null && secretKey != null) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            provider = new AWSStaticCredentialsProvider(credentials);
        } else {
            provider = new DefaultAWSCredentialsProviderChain();
        }

        AmazonEC2ClientBuilder client = AmazonEC2ClientBuilder.standard().withRegion(Regions.AP_EAST_1).withCredentials(provider);
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setProtocol(Protocol.HTTPS);
        return client.build();
    }

    public AmazonRoute53 getRoute53Client(String accessKey, String secretKey, String region) throws Exception {
        AWSCredentialsProvider provider;
        if (accessKey != null && secretKey != null) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            provider = new AWSStaticCredentialsProvider(credentials);
        } else {
            provider = new DefaultAWSCredentialsProviderChain();
        }

        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setProtocol(Protocol.HTTPS);
        return AmazonRoute53ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withClientConfiguration(configuration)
                .withCredentials(provider)
                .build();
    }

    //AmazonElasticLoadBalancingClient
    public AmazonElasticLoadBalancing  getELBClient(String accessKey, String secretKey, String region) throws Exception {
        AWSCredentialsProvider provider;
        if (accessKey != null && secretKey != null) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            provider = new AWSStaticCredentialsProvider(credentials);
        } else {
            provider = new DefaultAWSCredentialsProviderChain();
        }
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setProtocol(Protocol.HTTPS);
        return AmazonElasticLoadBalancingClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withClientConfiguration(configuration)
                .withCredentials(provider)
                .build();
    }

    public AmazonElasticLoadBalancing getELBClientv2(String accessKey, String secretKey, String region) throws Exception {
        AWSCredentialsProvider provider;
        if (accessKey != null && secretKey != null) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            provider = new AWSStaticCredentialsProvider(credentials);
        } else {
            provider = new DefaultAWSCredentialsProviderChain();
        }
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setProtocol(Protocol.HTTPS);
        return AmazonElasticLoadBalancingClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withClientConfiguration(configuration)
                .withCredentials(provider).build();
    }

    public AmazonIdentityManagement getIAM(String accessKey, String secretKey, String region) throws Exception {
        AWSCredentialsProvider provider;
        if (accessKey != null && secretKey != null) {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            provider = new AWSStaticCredentialsProvider(credentials);
        } else {
            provider = new DefaultAWSCredentialsProviderChain();
        }
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setProtocol(Protocol.HTTPS);

        return AmazonIdentityManagementClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withClientConfiguration(configuration)
                .withCredentials(provider)
                .build();
    }
}
