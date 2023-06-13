package com.example.rewardsprogram.api;

import com.example.rewardsprogram.application.CustomerService;
import com.example.rewardsprogram.application.RewardService;
import com.example.rewardsprogram.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private RewardService rewardService;

    @Test
    void getTotalRewardPoints() throws Exception {

        Customer customer = Customer.builder()
                .id(1L)
                .name("John")
                .transactions(new ArrayList<>())
                .build();

        when(customerService.findById(anyLong())).thenReturn(customer);

        when(rewardService.calculateTotalRewardPoints(anyList())).thenReturn(50);

        mockMvc.perform(get("/api/rewards/{customerId}/total", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getMonthlyRewardPoints() throws Exception {

        Customer customer = Customer.builder()
                .id(1L)
                .name("John")
                .transactions(new ArrayList<>())
                .build();

        when(customerService.findById(anyLong())).thenReturn(customer);

        Map<Month, Integer> mockMonthlyRewards = new HashMap<>();
        mockMonthlyRewards.put(Month.JANUARY, 10);
        mockMonthlyRewards.put(Month.FEBRUARY, 15);
        mockMonthlyRewards.put(Month.MARCH, 25);
        when(rewardService.calculateMonthlyRewardPoints(anyList())).thenReturn(mockMonthlyRewards);

        mockMvc.perform(get("/api/rewards/{customerId}/monthly", 1L))
                .andExpect(status().isOk());
    }
}