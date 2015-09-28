package de.sturmm.pcollect.repository.cassandra;

import com.datastax.driver.core.Session;
import de.sturmm.pcollect.model.Customer;
import de.sturmm.pcollect.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.Environment;
import reactor.fn.tuple.Tuple;
import reactor.fn.tuple.Tuple2;
import reactor.rx.Promise;
import reactor.rx.Promises;

import java.util.UUID;

/**
 * Created by sturmm on 18.09.15.
 */
@Repository
public class CassandraCustomerRepository implements CustomerRepository {

    private final Environment environment;
    private final Session session;
    private final CustomerMapper mapper;

    @Autowired
    public CassandraCustomerRepository(Environment environment, Session session, CustomerMapper mapper) {
        this.environment = environment;
        this.session = session;
        this.mapper = mapper;
    }

    @Override
    public Promise<Customer> save(final Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID().toString());
        }
        return Promises
                .task(environment, Environment.workDispatcher(), () -> Tuple.of(customer, mapper.toSaveStatement(customer)))
//                .syncTask(() -> Tuple.of(customer, mapper.toSaveStatement(customer)))
                .map(t -> Tuple.of(t.getT1(), session.execute(t.getT2())))
                .map(Tuple2::getT1)
                .map(t -> {
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return t;
                });
    }

    @Override
    public Promise<Customer> load(String id) {
        return Promises
                .task(environment, Environment.workDispatcher(), () -> mapper.toLoadByIdStatement(id))
                .map(session::execute)
                .map(mapper::toCustomer);
    }
}
