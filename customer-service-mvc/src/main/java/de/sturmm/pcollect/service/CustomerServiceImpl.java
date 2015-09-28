package de.sturmm.pcollect.service;

import de.sturmm.pcollect.model.Customer;
import de.sturmm.pcollect.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sturmm on 20.09.15.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public Customer load(String id) {
        return repo.load(id);
    }

    @Override
    public Customer save(Customer customer) {
        return repo.save(customer);
    }
}
