package de.sturmm.pcollect.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sturmm.pcollect.model.Customer;
import de.sturmm.pcollect.rest.model.LinkResponse;
import de.sturmm.pcollect.service.CustomerService;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.Environment;
import reactor.io.buffer.Buffer;
import reactor.io.net.http.HttpChannel;
import reactor.io.net.http.HttpServer;
import reactor.io.net.impl.netty.NettyChannelStream;
import reactor.rx.Stream;

import java.io.IOException;

/**
 * Created by sturmm on 20.09.15.
 */
@Component
public class CustomerResourceHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerResourceHandler.class);

    private final CustomerService customerService;
    private final ObjectMapper objectMapper;
//    private final JacksonJsonCodec<Buffer, Customer> codec;

    @Autowired
    public CustomerResourceHandler(HttpServer<Buffer, Buffer> server,
                                   CustomerService customerService,
                                   ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
//        this.codec = new JacksonJsonCodec<>(objectMapper);

        server.get("/customer/{id}", this::getCustomer);
        server.put("/customer", this::saveCustomer);
    }

    public Stream<Void> saveCustomer(HttpChannel<Buffer, Buffer> channel) {
        return channel.writeWith(
                channel.map(this::readCustomer)
                        .flatMap(customerService::save)
                        .map(this::respond)
                        .map(this::writeJson)
                        .map(Buffer::wrap)
        );
    }

    public Publisher<Void> getCustomer(HttpChannel<Buffer, Buffer> channel) {
        final String id = channel.params().get("id");
        return channel.writeWith(customerService.load(id)
                        .map(this::writeJson)
                        .map(Buffer::wrap)
        );
    }

    private String writeJson(Object customer) {
        try {
            return objectMapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Customer readCustomer(Buffer json) {
        NettyChannelStream
        try {
            return objectMapper.readValue(json.asString(), Customer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkResponse respond(Customer customer) {
        return new LinkResponse("/customer/" + customer.getId());
    }

    public <T> T log(T elem, String log) {
        LOG.info(log);
        return elem;
    }
}
