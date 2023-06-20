package com.example.rewardsprogram.api.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseData {
    private Long id;
    private String name;
    private BigDecimal amount;
}
