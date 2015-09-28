package de.sturmm.pcollect.service;

import de.sturmm.pcollect.model.Customer;
import de.sturmm.pcollect.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.rx.Promise;

/**
 * Created by sturmm on 20.09.15.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);


    private final CustomerRepository repo;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public Promise<Customer> load(String id) {
        return repo.load(id);
    }

    @Override
    public Promise<Customer> save(Customer customer) {
        return repo.save(customer);
    }
}
