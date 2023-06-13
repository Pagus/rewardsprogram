package com.example.rewardsprogram.api;

import com.example.rewardsprogram.api.model.PurchaseData;
import com.example.rewardsprogram.application.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private CustomerService customerService;

    public PurchaseController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String purchase(@RequestBody PurchaseData purchaseData) {
        customerService.save(purchaseData);
        return "Transaction created for customer";
    }
}
