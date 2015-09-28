package de.sturmm.pcollect.service;

import de.sturmm.pcollect.model.Customer;
import reactor.rx.Promise;

/**
 * Created by sturmm on 20.09.15.
 */
public interface CustomerService {

    Promise<Customer> load(String id);

    Promise<Customer> save(Customer customer);


}
