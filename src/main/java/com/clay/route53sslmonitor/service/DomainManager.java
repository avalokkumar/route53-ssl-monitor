package com.clay.route53sslmonitor.service;

import com.clay.route53sslmonitor.model.DomainRecord;

public interface DomainManager {

    DomainRecord getDomainInfo(String host);
}
