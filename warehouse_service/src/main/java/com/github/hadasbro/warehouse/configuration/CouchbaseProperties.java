package com.github.hadasbro.warehouse.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@Configuration
@PropertySource("classpath:couchbase.properties")
public class CouchbaseProperties {

    private final List<String> bootstrapHosts;
    private final String bucketName;
    private final String bucketPassword;
    private final String username;
    private final int port;

    /**
     * CouchbaseProperties
     *
     * @param bootstrapHosts -
     * @param bucketName -
     * @param bucketPassword -
     * @param port -
     * @param username -
     */
    public CouchbaseProperties(
            @Value("${spring.couchbase.bootstrap-hosts}") final List<String> bootstrapHosts,
            @Value("${spring.couchbase.bucket.name}") final String bucketName,
            @Value("${spring.couchbase.bucket.password}") final String bucketPassword,
            @Value("${spring.couchbase.port}") final int port,
            @Value("${spring.couchbase.username}") final String username
    ) {
        this.bootstrapHosts = Collections.unmodifiableList(bootstrapHosts);
        this.bucketName = bucketName;
        this.bucketPassword = bucketPassword;
        this.port = port;
        this.username = username;
    }

    /**
     * getBootstrapHosts
     *
     * @return List<String>
     */
    public List<String> getBootstrapHosts() {
        return bootstrapHosts;
    }

    /**
     * getBucketName
     *
     * @return String
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * getBucketPassword
     *
     * @return String
     */
    public String getBucketPassword() {
        return bucketPassword;
    }

    /**
     * getUsername
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * getPort
     *
     * @return int
     */
    public int getPort() {
        return port;
    }
}
