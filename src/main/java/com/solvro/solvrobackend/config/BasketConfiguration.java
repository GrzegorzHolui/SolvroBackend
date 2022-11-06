package com.solvro.solvrobackend.config;

import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.SummaryInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class BasketConfiguration {
    @Bean
    public Basket basket() {
        Basket basket = new Basket(new ArrayList<>(), "basketHash", new SummaryInfo());
        basket.getSummaryInfo().setUsedCard(new ArrayList<>());
        return basket;
    }
}
