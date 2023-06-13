package com.example.rewardsprogram.application;

import com.example.rewardsprogram.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RewardService {

    private static final int ONE_POINT_LIMIT = 50;
    private static final int TWO_POINTS_LIMIT = 100;

    public int calculateRewardPointsForTransaction(double amount) {
        log.debug("Calculating reward points for transaction amount: {}", amount);
        int points;
        if (amount <= ONE_POINT_LIMIT) {
            points = 0;
        } else if (amount <= TWO_POINTS_LIMIT) {
            points = (int) (amount - ONE_POINT_LIMIT);
        } else {
            points = (int) ((amount - TWO_POINTS_LIMIT) * 2 + ONE_POINT_LIMIT);
        }
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