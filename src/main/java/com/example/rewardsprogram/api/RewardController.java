package com.example.rewardsprogram.api;

import com.example.rewardsprogram.application.CustomerService;
import com.example.rewardsprogram.application.RewardService;
import com.example.rewardsprogram.domain.Customer;
import com.example.rewardsprogram.domain.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private CustomerService customerService;

    private RewardService rewardService;

    public RewardController(CustomerService customerService, RewardService rewardService) {
        this.customerService = customerService;
        this.rewardService = rewardService;
    }

    @GetMapping("/{customerId}/total")
    public ResponseEntity<Integer> getTotalRewardPoints(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Transaction> transactions = customer.getTransactions();
        int totalRewardPoints = rewardService.calculateTotalRewardPoints(transactions);
        return ResponseEntity.ok(totalRewardPoints);
    }

    @GetMapping("/{customerId}/monthly")
    public ResponseEntity<Map<Month, Integer>> getMonthlyRewardPoints(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Transaction> transactions = customer.getTransactions();
        Map<Month, Integer> monthlyRewardPoints = rewardService.calculateMonthlyRewardPoints(transactions);
        return ResponseEntity.ok(monthlyRewardPoints);
    }
}
