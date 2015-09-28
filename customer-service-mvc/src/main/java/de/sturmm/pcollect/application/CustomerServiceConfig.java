package de.sturmm.pcollect.application;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sturmm.pcollect.repository.CustomerRepository;
import de.sturmm.pcollect.rest.CustomerResourceHandler;
import de.sturmm.pcollect.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackageClasses = {CustomerRepository.class, CustomerService.class, CustomerResourceHandler.class})
@EnableAutoConfiguration
public class CustomerServiceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceConfig.class);

    @Bean(destroyMethod = "close")
    public Cluster cluster(CassandraConfig conf) {
        return Cluster.buildFrom(conf);
    }

    @Bean(destroyMethod = "close")
    public Session session(CassandraConfig conf, Cluster cluster) throws Exception {
        return cluster.connect(conf.getKeyspace());
    }

    @Bean
    public CassandraConfig cassandraConfig() {
        return new CassandraConfig();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer config() {
        final PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
        config.setLocation(new ClassPathResource("cassandra.properties"));

        return config;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}