package com.futureforward.futureforwarddemo.service;

import com.futureforward.futureforwarddemo.entity.Customer;
import com.futureforward.futureforwarddemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
}
