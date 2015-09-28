package de.sturmm.pcollect.repository.cassandra;

import com.datastax.driver.core.Session;
import de.sturmm.pcollect.model.Customer;
import de.sturmm.pcollect.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by sturmm on 18.09.15.
 */
@Repository
public class CassandraCustomerRepository implements CustomerRepository {

    private final Session session;
    private final CustomerMapper mapper;

    @Autowired
    public CassandraCustomerRepository(Session session, CustomerMapper mapper) {
        this.session = session;
        this.mapper = mapper;
    }

    @Override
    @SneakyThrows
    public Customer save(final Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID().toString());
        }

        session.execute(mapper.toSaveStatement(customer));

        Thread.sleep(1000l);

        return customer;
    }

    @Override
    public Customer load(String id) {
        return mapper.toCustomer(session.execute(mapper.toLoadByIdStatement(id)));
    }
}
