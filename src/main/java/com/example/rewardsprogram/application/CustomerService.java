package com.example.rewardsprogram.application;

import com.example.rewardsprogram.api.model.PurchaseData;
import com.example.rewardsprogram.domain.Customer;
import com.example.rewardsprogram.domain.Transaction;
import com.example.rewardsprogram.exception.exceptions.CustomerNotFoundException;
import com.example.rewardsprogram.exception.exceptions.UnprocessableEntityException;
import com.example.rewardsprogram.infrastructure.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", id);
                    return new CustomerNotFoundException("Customer not found with id: " + id);
                });
    }

    public Customer save(PurchaseData purchaseData) {
        log.debug("Request to save Customer : {}", purchaseData);

        if (purchaseData == null || purchaseData.getId() == null || purchaseData.getName() == null) {
            log.error("Invalid PurchaseData: {}", purchaseData);
            throw new UnprocessableEntityException("Invalid PurchaseData - nulls are not allowed");
        }

        Customer customer = customerRepository.findById(purchaseData.getId())
                .orElseGet(() -> Customer.builder()
                        .id(purchaseData.getId())
                        .name(purchaseData.getName())
                        .transactions(new ArrayList<>())
                        .build());

        customer.addTransaction(Transaction.builder()
                .amount(purchaseData.getAmount())
                .customer(customer)
                .date(LocalDate.now())
                .build());

        customerRepository.save(customer);
        log.debug("Saved Customer : {}", customer);
        return customer;
    }
}