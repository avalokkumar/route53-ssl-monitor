package com.clay.route53sslmonitor.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "domain_record")
@Data
@EqualsAndHashCode(callSuper = false)
public class DomainRecord extends Auditable {

    @Id
    @Column(name = "id",columnDefinition = "BIGINT")
    public BigInteger id;

    @Column(name = "domain",nullable = false)
    public String domain;

    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    public RegionCode region;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public DomainStatus status;

    @Column(name = "sub_domain")
    public String subDomain;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public DomainType type;

    @Column(name = "value")
    public String value;

    @Column(name = "weight")
    public Integer weight;

}

