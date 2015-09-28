package de.sturmm.pcollect.repository;

import de.sturmm.pcollect.model.Customer;
import org.springframework.stereotype.Repository;
import reactor.rx.Promise;

/**
 * Created by sturmm on 18.09.15.
 */
@Repository
public interface CustomerRepository {

    Promise<Customer> save(Customer customer);

    Promise<Customer> load(String id);

}
