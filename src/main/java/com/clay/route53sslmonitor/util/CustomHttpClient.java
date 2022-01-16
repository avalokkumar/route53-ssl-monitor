package com.clay.route53sslmonitor.util;

import com.amazonaws.services.route53.model.RRType;
import com.amazonaws.services.route53.model.ResourceRecordSet;
import com.clay.route53sslmonitor.model.DomainRecord;
import com.clay.route53sslmonitor.model.HostDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.BiPredicate;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Service
public class CustomHttpClient {

    public static final String HTTPS_FORMAT = "https://%s";

    @Value(value = "${cert.expiry.threshold.days: 30}")
    private Integer expiryThresholdDays;

    @Value("${route53.read-timout:500}")
    private Integer readTimeoutInMs;

    @Value("${route53.connect-timout:500}")
    private Integer connectTimeoutInMs;

    public Pair<ExpiryStatus, HostDetails> getCertExpiryHostDetails(ResourceRecordSet recordSet, String host) {
        HttpsURLConnection conn = null;
        Pair<ExpiryStatus, HostDetails> hostDetailsPair = null;
        try {
            final var url = new URL(format(HTTPS_FORMAT, host));
            conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(readTimeoutInMs);
            conn.setConnectTimeout(connectTimeoutInMs);
            conn.connect();

            if (ArrayUtils.isEmpty(conn.getServerCertificates())) {
                var cert = conn.getServerCertificates()[0];
                if (cert instanceof X509Certificate) {
                    var x509 = (X509Certificate) cert;
                    Date expiryDate = x509.getNotAfter();
                    try {
                        x509.checkValidity();
                        final var days = DAYS.convert(expiryDate.getTime() - new Date().getTime(), MILLISECONDS);
                        if (days <= expiryThresholdDays) {
                            hostDetailsPair = buildHostDetailsPairs(ExpiryStatus.NEAR_EXPIRY, host, expiryDate.toInstant());
                            log.info("Certificate will expire in {} days", days);
                        }
                    } catch (CertificateExpiredException cee) {
                        log.info("Certificate is expired at {} ", LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()).toString());
                        hostDetailsPair = buildHostDetailsPairs(ExpiryStatus.EXPIRED, host, expiryDate.toInstant());
                    }
                }
            }
        } catch (Exception e) {
            var cause = e.getCause() != null ? e.getCause() : e;
            log.info(format("Invalid SSL connection in %s - error: %s - %s", recordSet.getName(),
                e.getMessage(), cause.getClass()));

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        log.info("label=CustomHttpClient.getCertExpiryHostDetails : hostDetailsPair {}", hostDetailsPair);
        return hostDetailsPair;
    }

    private Pair<ExpiryStatus, HostDetails> buildHostDetailsPairs(ExpiryStatus expiryStatus, String host, Instant expiryDate) {
        return Pair.of(
            expiryStatus,
            HostDetails.builder()
                .host(host)
                .certExpiryDate(expiryDate)
                .build()
        );
    }

    /**
     * BiPredicate to check if it should be checked and if it is listening to 443 port
     */
    public BiPredicate<ResourceRecordSet, DomainRecord> validateInput = (recordSet1, organisationDomainRecord1)
        -> !shouldBeChecked(recordSet1) || !isListeningForSsl(recordSet1);

    private boolean shouldBeChecked(ResourceRecordSet rs) {
        return rs.getType().equals(RRType.CNAME.name())
            || rs.getType().equals(RRType.A.name());
    }

    /**
     * Method to check if the host is listening to secure port 443
     *
     * @param resourceRecordSet
     * @return
     */
    private boolean isListeningForSsl(ResourceRecordSet resourceRecordSet) {
        try (var socket = new Socket()) {
            socket.connect(new InetSocketAddress(resourceRecordSet.getName(), 443), connectTimeoutInMs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
