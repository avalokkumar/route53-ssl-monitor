package com.clay.route53sslmonitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpirationResult {

    private static final String NO_RESULT = "There is no expired or near expiry certificate";
    private static final String EXPIRED_CERTIFICATES = "Expired certificates in our DNS";
    private static final String CERTIFICATES_NEAR_EXPIRY = "Certificates that are near expiry";

    @JsonProperty("expired")
    private final List<HostDetails> expiredHosts;

    @JsonProperty("neary_expiry")
    private final List<HostDetails> nearExpirationHosts;

    public ExpirationResult() {
        this.expiredHosts = new ArrayList<>();
        this.nearExpirationHosts = new ArrayList<>();
    }

    public void addExpiredHost(HostDetails hostDetails) {
        this.expiredHosts.add(
            HostDetails.builder()
                .host(hostDetails.getHost())
                .certExpiryDate(hostDetails.getCertExpiryDate())
                .build()
        );
    }

    public void addNearExpirationHost(HostDetails hostDetails) {
        this.nearExpirationHosts.add(
            HostDetails.builder()
                .host(hostDetails.getHost())
                .certExpiryDate(hostDetails.getCertExpiryDate())
                .build()
        );
    }

    @Override
    public String toString() {
        if (expiredHosts.isEmpty() && nearExpirationHosts.isEmpty()) {
            return NO_RESULT;
        }
        final var payload = new StringBuilder();
        if (!expiredHosts.isEmpty()) {
            payload.append(EXPIRED_CERTIFICATES + "\n");
            payload.append("```\n");
            expiredHosts.forEach(item -> payload.append(format("%s\n", item)));
            payload.append("```\n");
        }
        if (!nearExpirationHosts.isEmpty()) {
            payload.append(CERTIFICATES_NEAR_EXPIRY);
            payload.append("\n");
            payload.append("```\n");
            nearExpirationHosts.forEach(item -> payload.append(format("%s\n", item)));
            payload.append("```\n");
        }
        return payload.toString();
    }
}
