package com.example.rewardsprogram.application;

import com.example.rewardsprogram.domain.Transaction;
import com.example.rewardsprogram.exception.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RewardServiceTest {

    @InjectMocks
    RewardService rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calculateRewardPointsForTransaction() {
        assertEquals(0, rewardService.calculateRewardPointsForTransaction(BigDecimal.valueOf(40)));

        assertEquals(10, rewardService.calculateRewardPointsForTransaction(BigDecimal.valueOf(60)));

        assertEquals(70, rewardService.calculateRewardPointsForTransaction(BigDecimal.valueOf(110)));
    }

    @Test
    void calculateTotalRewardPoints() {
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().amount(BigDecimal.valueOf(40)).date(LocalDate.now()).build(),
                Transaction.builder().amount(BigDecimal.valueOf(60)).date(LocalDate.now()).build(),
                Transaction.builder().amount(BigDecimal.valueOf(110)).date(LocalDate.now()).build()
        );
        assertEquals(80, rewardService.calculateTotalRewardPoints(transactions));
    }

    @Test
    void calculateMonthlyRewardPoints() {
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().amount(BigDecimal.valueOf(40)).date(LocalDate.of(2023, Month.JANUARY, 1)).build(),
                Transaction.builder().amount(BigDecimal.valueOf(60)).date(LocalDate.of(2023, Month.JANUARY, 1)).build(),
                Transaction.builder().amount(BigDecimal.valueOf(110)).date(LocalDate.of(2023, Month.FEBRUARY, 1)).build()
        );
        Map<Month, Integer> monthlyPoints = rewardService.calculateMonthlyRewardPoints(transactions);
        assertEquals(10, monthlyPoints.get(Month.JANUARY));
        assertEquals(70, monthlyPoints.get(Month.FEBRUARY));
    }

    @Test
    void calculateRewardPointsForTransactionNullAmountThrowsInvalidDataException() {
        Exception exception = assertThrows(InvalidDataException.class, () ->
                rewardService.calculateRewardPointsForTransaction(null));

        String expectedMessage = "Transaction amount cannot be null";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}