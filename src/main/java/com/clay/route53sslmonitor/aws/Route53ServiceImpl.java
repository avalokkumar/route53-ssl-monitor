package com.clay.route53sslmonitor.aws;

import com.amazonaws.services.config.AmazonConfig;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.model.*;
import com.clay.route53sslmonitor.config.AWSConfig;
import com.clay.route53sslmonitor.model.DomainRecord;
import com.clay.route53sslmonitor.model.HostDetails;
import com.clay.route53sslmonitor.model.RegionCode;
import com.clay.route53sslmonitor.service.DomainManager;
import com.clay.route53sslmonitor.util.CustomHttpClient;
import com.clay.route53sslmonitor.util.ExpiryStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@EnableConfigurationProperties({AWSConfig.class})
@Component(value = "route53Service")
public class Route53ServiceImpl implements Route53Service {

    private AmazonRoute53 r53Client;
    private String region;

    @Autowired
    private DomainManager domainManager;

    @Autowired
    private CustomHttpClient customHttpClient;

    public Route53ServiceImpl(AmazonRoute53 r53Client, AWSConfig awsConfig) {
        this.r53Client = r53Client;
        this.region = awsConfig.getRegion() != null ? awsConfig.getRegion() : RegionCode.US.getRegionCodes()[0];
    }

    @Override
    public Map<ExpiryStatus, List<HostDetails>> getCertificateExpiryDetails() {

        ListHostedZonesResult hostedZonesResult = r53Client.listHostedZones();

        Map<ExpiryStatus, List<HostDetails>> certExpiryMapping = new HashMap<>();
        certExpiryMapping.put(ExpiryStatus.NEAR_EXPIRY, new ArrayList<>());
        certExpiryMapping.put(ExpiryStatus.EXPIRED, new ArrayList<>());

        hostedZonesResult.getHostedZones()
            .forEach(hostedZone -> getExpiryDetailsByStatus(hostedZone, certExpiryMapping));

        return certExpiryMapping;
    }

    private void getExpiryDetailsByStatus(HostedZone hostedZone, Map<ExpiryStatus, List<HostDetails>> certExpiryMapping) {

        ListResourceRecordSetsRequest resourceRecordSetsRequest = new ListResourceRecordSetsRequest();
        resourceRecordSetsRequest.setHostedZoneId(hostedZone.getId());

        final var recordSets = r53Client.listResourceRecordSets(resourceRecordSetsRequest);

        recordSets.getResourceRecordSets()
            .forEach(recordSet -> {
                    final var host = recordSet.getName().substring(0, recordSet.getName().length() - 1);
                    DomainRecord domainInfo = domainManager.getDomainInfo(host);
                    if (customHttpClient.validateInput.test(recordSet, domainInfo)) {
                        return;
                    }
                    Pair<ExpiryStatus, HostDetails> certExpiryHostDetails = customHttpClient.getCertExpiryHostDetails(recordSet, host);
                    certExpiryMapping.get(certExpiryHostDetails.getFirst()).add(certExpiryHostDetails.getSecond());
                }
            );
    }
}
