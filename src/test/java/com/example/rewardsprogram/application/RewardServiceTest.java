package com.example.rewardsprogram.application;

import com.example.rewardsprogram.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardServiceTest {

    @InjectMocks
    RewardService rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calculateRewardPointsForTransaction() {
        assertEquals(0, rewardService.calculateRewardPointsForTransaction(40));

        assertEquals(10, rewardService.calculateRewardPointsForTransaction(60));

        assertEquals(70, rewardService.calculateRewardPointsForTransaction(110));
    }

    @Test
    void calculateTotalRewardPoints() {
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().amount(40).date(LocalDate.now()).build(),
                Transaction.builder().amount(60).date(LocalDate.now()).build(),
                Transaction.builder().amount(110).date(LocalDate.now()).build()
        );
        assertEquals(80, rewardService.calculateTotalRewardPoints(transactions));
    }

    @Test
    void calculateMonthlyRewardPoints() {
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().amount(40).date(LocalDate.of(2023, Month.JANUARY, 1)).build(),
                Transaction.builder().amount(60).date(LocalDate.of(2023, Month.JANUARY, 1)).build(),
                Transaction.builder().amount(110).date(LocalDate.of(2023, Month.FEBRUARY, 1)).build()
        );
        Map<Month, Integer> monthlyPoints = rewardService.calculateMonthlyRewardPoints(transactions);
        assertEquals(10, monthlyPoints.get(Month.JANUARY));
        assertEquals(70, monthlyPoints.get(Month.FEBRUARY));
    }
}