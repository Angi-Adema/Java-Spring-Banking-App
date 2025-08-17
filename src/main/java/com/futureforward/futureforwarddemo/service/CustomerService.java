package com.futureforward.futureforwarddemo.service;

import com.futureforward.futureforwarddemo.dto.CreateCustomerRequest;
import com.futureforward.futureforwarddemo.dto.CustomerResponse;
import com.futureforward.futureforwarddemo.dto.UpdateCustomerRequest;
import com.futureforward.futureforwarddemo.entity.Customer;
import com.futureforward.futureforwarddemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Optional, only if spring-boot-starter-security is on the classpath
    // @Autowired(required = false)
    // private PasswordEncoder passwordEncoder;

    // CREATE (DTO-based)
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }

        Customer c = new Customer();
        c.setFirstName(request.getFirstName());
        c.setLastName(request.getLastName());
        c.setEmail(request.getEmail());

        // hash if encoder available, else store raw (dev-only)
        //String raw = request.getPassword();
        c.setPassword(request.getPassword());
        //c.setPassword(passwordEncoder != null ? passwordEncoder.encode(raw) : raw);

        Customer saved = customerRepository.save(c);
        return mapToResponse(saved);
    }

    // READ ALL
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // READ ONE
    public Optional<CustomerResponse> getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::mapToResponse);
    }

    // READ ONE (Entity) — used by other services/controllers that need the entity
    public Optional<Customer> findEntityById(Long id) {
        return customerRepository.findById(id);
    }

    // UPDATE DTO (no password here)
    public Optional<CustomerResponse> updateCustomer(Long id, UpdateCustomerRequest request) {
        return customerRepository.findById(id).map(existing -> {
            if (!existing.getEmail().equalsIgnoreCase(request.getEmail())
                    && customerRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("A customer with this email already exists.");
            }
            existing.setFirstName(request.getFirstName());
            existing.setLastName(request.getLastName());
            existing.setEmail(request.getEmail());
            return mapToResponse(customerRepository.save(existing));
        });
    }

    // DELETE
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ENTITY -> DTO
    public CustomerResponse mapToResponse(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.setId(c.getId());
        r.setFirstName(c.getFirstName());
        r.setLastName(c.getLastName());
        r.setEmail(c.getEmail());
        return r;
    }
}
