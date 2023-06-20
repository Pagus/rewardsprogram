package com.example.rewardsprogram.application;

import com.example.rewardsprogram.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RewardService {

    private static final BigDecimal ONE_POINT_LIMIT = new BigDecimal(50);
    private static final BigDecimal TWO_POINTS_LIMIT = new BigDecimal(100);

    public int calculateRewardPointsForTransaction(BigDecimal amount) {
        log.debug("Calculating reward points for transaction amount: {}", amount);

        BigDecimal amountValue = amount;
        if (amountValue.compareTo(ONE_POINT_LIMIT) <= 0) {
            log.debug("Reward points for transaction amount {}: {}", amount, 0);
            return 0;
        }
        if (amountValue.compareTo(TWO_POINTS_LIMIT) <= 0) {
            int points = amountValue.subtract(ONE_POINT_LIMIT).intValue();
            log.debug("Reward points for transaction amount {}: {}", amount, points);
            return points;
        }
        int points = amountValue.subtract(TWO_POINTS_LIMIT).multiply(new BigDecimal(2)).add(ONE_POINT_LIMIT).intValue();
        log.debug("Reward points for transaction amount {}: {}", amount, points);
        return points;
    }

    public int calculateTotalRewardPoints(List<Transaction> transactions) {
        log.debug("Calculating total reward points for transactions");
        int totalPoints = transactions.stream()
                .mapToInt(t -> calculateRewardPointsForTransaction(t.getAmount()))
                .sum();
        log.debug("Total reward points: {}", totalPoints);
        return totalPoints;
    }

    public Map<Month, Integer> calculateMonthlyRewardPoints(List<Transaction> transactions) {
        log.debug("Calculating monthly reward points");
        Map<Month, Integer> monthlyRewardPoints = new HashMap<>();
        for (Transaction transaction : transactions) {
            Month month = transaction.getDate().getMonth();
            int rewardPointsForTransaction = calculateRewardPointsForTransaction(transaction.getAmount());
            monthlyRewardPoints.merge(month, rewardPointsForTransaction, Integer::sum);
            log.debug("For month {} added reward points: {}", month, rewardPointsForTransaction);
        }
        return monthlyRewardPoints;
    }
}