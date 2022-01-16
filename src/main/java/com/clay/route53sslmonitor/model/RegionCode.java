package com.clay.route53sslmonitor.model;

public enum RegionCode {

    UNSPECIFIED("unspecified"),
    US("us-east-1", "us-east-2", "us-west-1", "us-west-2"),
    EU("eu-west-1", "eu-west-2", "eu-west-3", "eu-central-1"),
    AU("ap-southeast-2", "ap-southeast-1", "ap-northeast-2", "ap-northeast-1", "ap-south-1"),
    IN("ap-south-1", "ap-northeast-1", "ap-northeast-2", "ap-southeast-1", "ap-southeast-2");

    private final String[] regionCodes;

    private RegionCode(String... regionCodes) {
        this.regionCodes = regionCodes;
    }

    public String[] getRegionCodes() {
        return regionCodes;
    }

    public static RegionCode fromString(String regionCode) {
        return RegionCode.valueOf(regionCode.toUpperCase());
    }
}
