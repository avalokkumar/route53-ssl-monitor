package com.clay.route53sslmonitor.service;

import com.clay.route53sslmonitor.aws.Route53ServiceImpl;
import com.clay.route53sslmonitor.model.ExpirationResult;
import com.clay.route53sslmonitor.model.HostDetails;
import com.clay.route53sslmonitor.util.Constant;
import com.clay.route53sslmonitor.util.ExpiryStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DomainServiceImpl implements DomainService {

    private static final Integer PAGE_SIZE = 100;

    @Autowired
    @Qualifier(value = "route53Service")
    private Route53ServiceImpl route53Service;

    @Override
    public ExpirationResult getDomainsCertExpiryDetails(Integer pageNumber, Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = Constant.DEFAULT_PAGE_NUMBER;
        }
        if (pageSize <= 0 || pageSize > PAGE_SIZE) {
            pageSize = PAGE_SIZE;
        }
        Map<ExpiryStatus, List<HostDetails>> certExpiryDetails = route53Service.getCertificateExpiryDetails();

        return buildExpirationResult(certExpiryDetails);
    }

    private ExpirationResult buildExpirationResult(Map<ExpiryStatus, List<HostDetails>> certExpiryDetails) {
        ExpirationResult expirationResult = new ExpirationResult();

        certExpiryDetails.get(ExpiryStatus.EXPIRED)
            .forEach(expirationResult::addExpiredHost);
        certExpiryDetails.get(ExpiryStatus.NEAR_EXPIRY)
            .forEach(expirationResult::addNearExpirationHost);

        return expirationResult;
    }
}
