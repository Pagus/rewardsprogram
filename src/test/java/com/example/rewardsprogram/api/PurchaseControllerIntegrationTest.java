package com.example.rewardsprogram.api;

import com.example.rewardsprogram.api.model.PurchaseData;
import com.example.rewardsprogram.application.CustomerService;
import com.example.rewardsprogram.domain.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void shouldCreatePurchase() throws Exception {
        PurchaseData purchaseData = new PurchaseData();
        purchaseData.setId(1L);
        purchaseData.setName("John");
        purchaseData.setAmount(100);

        Customer customer = Customer.builder()
                .id(purchaseData.getId())
                .name(purchaseData.getName())
                .transactions(new ArrayList<>())
                .build();

        when(customerService.save(any(PurchaseData.class))).thenReturn(customer);

        ObjectMapper objectMapper = new ObjectMapper();
        String purchaseDataJson = objectMapper.writeValueAsString(purchaseData);

        mockMvc.perform(post("/api/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseDataJson))
                .andExpect(status().isCreated());
    }
}