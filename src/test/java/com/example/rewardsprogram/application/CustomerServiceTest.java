package com.example.rewardsprogram.application;

import com.example.rewardsprogram.api.model.PurchaseData;
import com.example.rewardsprogram.domain.Customer;
import com.example.rewardsprogram.exception.exceptions.CustomerNotFoundException;
import com.example.rewardsprogram.exception.exceptions.UnprocessableEntityException;
import com.example.rewardsprogram.infrastructure.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static final String EXPECTED_MESSAGE_FOR_UNPROCESSABLE_EXCEPTION = "Invalid PurchaseData - nulls are not allowed";

    @Test
    void findAll() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        List<Customer> customers = customerService.findAll();
        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findByIdFound() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.of(new Customer()));
        Customer customer = customerService.findById(id);
        assertNotNull(customer);
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotFound() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(id));
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void save() {
        Long id = 1L;
        PurchaseData purchaseData = new PurchaseData();
        purchaseData.setId(id);
        purchaseData.setName("Test");
        purchaseData.setAmount(BigDecimal.valueOf(100));

        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        Customer savedCustomer = customerService.save(purchaseData);

        assertNotNull(savedCustomer);
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void save_nullPurchaseDataThrowsUnprocessableEntityException() {
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> customerService.save(null));

        String actualMessage = exception.getMessage();

        assertEquals(EXPECTED_MESSAGE_FOR_UNPROCESSABLE_EXCEPTION, actualMessage);
    }

    @Test
    public void save_nullIdInPurchaseDataThrowsUnprocessableEntityException() {
        PurchaseData purchaseData = new PurchaseData();
        purchaseData.setId(null);
        purchaseData.setName("Test Name");
        purchaseData.setAmount(new BigDecimal("100"));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> customerService.save(purchaseData));

        String actualMessage = exception.getMessage();

        assertEquals(EXPECTED_MESSAGE_FOR_UNPROCESSABLE_EXCEPTION, actualMessage);
    }

    @Test
    public void save_nullNameInPurchaseDataThrowsUnprocessableEntityException() {
        PurchaseData purchaseData = new PurchaseData();
        purchaseData.setId(1L);
        purchaseData.setName(null);
        purchaseData.setAmount(new BigDecimal("100"));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> customerService.save(purchaseData));

        String actualMessage = exception.getMessage();

        assertEquals(EXPECTED_MESSAGE_FOR_UNPROCESSABLE_EXCEPTION, actualMessage);
    }
}