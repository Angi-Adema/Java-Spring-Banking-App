package com.futureforward.futureforwarddemo.controller;

import com.futureforward.futureforwarddemo.dto.CreateCustomerRequest;
import com.futureforward.futureforwarddemo.dto.CustomerResponse;
import com.futureforward.futureforwarddemo.dto.UpdateCustomerRequest;
import com.futureforward.futureforwarddemo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request) {
        return customerService.updateCustomer(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
