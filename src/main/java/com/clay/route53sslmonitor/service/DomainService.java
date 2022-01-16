package com.clay.route53sslmonitor.service;

import com.clay.route53sslmonitor.model.ExpirationResult;

public interface DomainService {

    ExpirationResult getDomainsCertExpiryDetails(Integer pageNumber, Integer pageSize);
}
