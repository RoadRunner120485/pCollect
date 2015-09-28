package de.sturmm.pcollect.application;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sturmm.pcollect.repository.CustomerRepository;
import de.sturmm.pcollect.rest.CustomerResourceHandler;
import de.sturmm.pcollect.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import reactor.Environment;
import reactor.core.dispatch.RingBufferDispatcher;
import reactor.core.dispatch.WorkQueueDispatcher;
import reactor.io.buffer.Buffer;
import reactor.io.net.NetStreams;
import reactor.io.net.http.HttpServer;

@Configuration
@ComponentScan(basePackageClasses = {CustomerRepository.class, CustomerService.class, CustomerResourceHandler.class})
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

    @Bean(destroyMethod = "close")
    public reactor.Environment environment() {
        return reactor.Environment.initialize();
    }

    @Bean
    public HttpServer<Buffer, Buffer> bufferHttpServer(Environment env) {
//        WorkQueueDispatcher http = new WorkQueueDispatcher("HTTP", 32, 1024, Throwable::printStackTrace);
        HttpServer<Buffer, Buffer> server = NetStreams.httpServer(s ->
            s.listen(8081)
        );
        server.start();
        return server;
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