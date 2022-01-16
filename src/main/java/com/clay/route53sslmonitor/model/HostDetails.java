package com.clay.route53sslmonitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostDetails {

    @JsonProperty("host")
    private String host;

    @JsonProperty("certificate_expiry_date")
    protected Instant certExpiryDate;
}
