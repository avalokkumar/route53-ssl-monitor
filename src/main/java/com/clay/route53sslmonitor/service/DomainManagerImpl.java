package com.clay.route53sslmonitor.service;

import com.clay.route53sslmonitor.model.DomainRecord;
import org.springframework.stereotype.Component;

@Component
public class DomainManagerImpl implements DomainManager {

    @Override
    public DomainRecord getDomainInfo(String host) {
        return null;
    }
}
