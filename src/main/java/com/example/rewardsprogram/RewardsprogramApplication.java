package com.example.rewardsprogram;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/")}, info = @Info(title = "Rewards Program DEMO API", version = "1.0"))
public class RewardsprogramApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardsprogramApplication.class, args);
    }

}
