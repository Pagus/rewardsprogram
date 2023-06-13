package com.example.rewardsprogram.api.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseData {

    private Long id;
    private String name;
    private double amount;
}
