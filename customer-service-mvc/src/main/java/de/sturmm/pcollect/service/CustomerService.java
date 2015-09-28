package de.sturmm.pcollect.service;

import de.sturmm.pcollect.model.Customer;

/**
 * Created by sturmm on 20.09.15.
 */
public interface CustomerService {

    Customer load(String id);

    Customer save(Customer customer);


}
