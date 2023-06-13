package com.example.rewardsprogram.api;

import com.example.rewardsprogram.application.CustomerService;
import com.example.rewardsprogram.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() throws Exception {
        Customer customer = Customer.builder().id(1L).name("John").build();
        List<Customer> allCustomers = Arrays.asList(customer);

        given(customerService.findAll()).willReturn(allCustomers);

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCustomerById() throws Exception {
        Customer customer = Customer.builder().id(1L).name("John").build();

        given(customerService.findById(1L)).willReturn(customer);

        mockMvc.perform(get("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}