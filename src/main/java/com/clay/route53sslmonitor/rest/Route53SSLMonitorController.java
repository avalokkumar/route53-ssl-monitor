package com.clay.route53sslmonitor.rest;

import com.clay.route53sslmonitor.model.ExpirationResult;
import com.clay.route53sslmonitor.service.DomainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Route53SSLMonitorController {

    @RequestMapping("/hello")
    public String sayHello() {
        return "Greetings from Route53 SSL Monitor!";
    }


    @Autowired
    private DomainServiceImpl domainService;

    @GetMapping("/domains/expiry/status")
    public ExpirationResult getInternalDomains(@RequestParam("pageNumber") String pageNumber, @RequestParam("pageSize") String pageSize) {

        return domainService.getDomainsCertExpiryDetails(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
    }

}