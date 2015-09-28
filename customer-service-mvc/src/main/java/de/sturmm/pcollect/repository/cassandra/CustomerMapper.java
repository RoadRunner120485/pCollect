package de.sturmm.pcollect.repository.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import de.sturmm.pcollect.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sturmm on 20.09.15.
 */
@Component
public class CustomerMapper {

    private final Mapper<Customer> mapper;

    @Autowired
    public CustomerMapper(Session session) {
        mapper = new MappingManager(session).mapper(Customer.class);
    }

    public Statement toSaveStatement(Customer customer) {
        return mapper.saveQuery(customer);
    }

    public Statement toLoadByIdStatement(String id) {
        return mapper.getQuery(id);
    }

    public Customer toCustomer(ResultSet resultSet) {
        return mapper.map(resultSet).one();
    }

}
