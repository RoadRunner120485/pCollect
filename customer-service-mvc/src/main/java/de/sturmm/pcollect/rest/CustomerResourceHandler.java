package de.sturmm.pcollect.rest;

import de.sturmm.pcollect.model.Customer;
import de.sturmm.pcollect.rest.model.LinkResponse;
import de.sturmm.pcollect.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sturmm on 20.09.15.
 */
@RestController
public class CustomerResourceHandler {

    private final CustomerService customerService;

    @Autowired
    public CustomerResourceHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/customer")
    public LinkResponse saveCustomer(@RequestBody Customer customer) {
        return respond(customerService.save(customer));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{id}")
    public Customer getCustomer(@PathVariable("id") String id) {
        return customerService.load(id);
    }

    private LinkResponse respond(Customer customer) {
        return new LinkResponse("/customer/" + customer.getId());
    }
}
