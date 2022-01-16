package com.clay.route53sslmonitor.aws;


import com.clay.route53sslmonitor.model.HostDetails;
import com.clay.route53sslmonitor.util.ExpiryStatus;

import java.util.List;
import java.util.Map;

public interface Route53Service {

    Map<ExpiryStatus, List<HostDetails>> getCertificateExpiryDetails();

}
