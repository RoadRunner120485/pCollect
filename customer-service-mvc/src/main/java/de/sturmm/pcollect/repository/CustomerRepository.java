package de.sturmm.pcollect.repository;

import de.sturmm.pcollect.model.Customer;

/**
 * Created by sturmm on 18.09.15.
 */
public interface CustomerRepository {

    Customer save(Customer customer);

    Customer load(String id);

}
