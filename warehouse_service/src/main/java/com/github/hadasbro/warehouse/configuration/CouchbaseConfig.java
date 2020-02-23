package com.github.hadasbro.warehouse.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

import java.util.List;

@Configuration
@SuppressWarnings({"unused"})
@EnableReactiveCouchbaseRepositories
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    /**
     * CouchbaseProperties
     */
    private CouchbaseProperties properties;

    /**
     * CouchbaseConfig
     *
     * @param properties -
     */
    public CouchbaseConfig(CouchbaseProperties properties) {
        this.properties = properties;
    }

    /**
     * getBootstrapHosts
     *
     * @return List<String>
     */
    @Override
    protected List<String> getBootstrapHosts() {
        return this.properties.getBootstrapHosts();
    }

    /**
     * getBucketName
     *
     * @return String
     */
    @Override
    protected String getBucketName() {
        return this.properties.getBucketName();
    }

    /**
     * getUsername
     *
     * @return getBucketName
     */
    @Override
    protected String getUsername() {
        return this.properties.getUsername();
    }

    /**
     * getBucketPassword
     *
     * @return getBucketName
     */
    @Override
    protected String getBucketPassword() {
        return this.properties.getBucketPassword();
    }
}
