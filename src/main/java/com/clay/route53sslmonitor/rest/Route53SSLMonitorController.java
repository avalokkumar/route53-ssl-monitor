package com.clay.route53sslmonitor.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Route53SSLMonitorController {

    @RequestMapping("/hello")
    public String sayHello() {
        return "Greetings from Route53 SSL Monitor!";
    }


}