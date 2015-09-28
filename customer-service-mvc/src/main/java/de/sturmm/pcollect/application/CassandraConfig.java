package de.sturmm.pcollect.application;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Configuration;
import com.datastax.driver.core.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by sturmm on 20.09.15.
 */
@Component
public class CassandraConfig implements Cluster.Initializer {

    @Value("${cassandra.contactpoints}")
    private String contactpoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    public String getKeyspace() {
        return keyspace;
    }

    @Override
    public String getClusterName() {
        return "pCollectCluster";
    }

    @Override
    public List<InetSocketAddress> getContactPoints() {
        return Arrays.asList(new InetSocketAddress(contactpoints, port));
    }

    @Override
    public Configuration getConfiguration() {
        return Configuration.builder().build();
    }

    @Override
    public Collection<Host.StateListener> getInitialListeners() {
        return Collections.emptyList();
    }
}
