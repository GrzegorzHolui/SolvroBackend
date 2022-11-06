package com.solvro.solvrobackend.config;

import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.model.TypeOfDiscount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataBaseItemConfiguration {
    @Bean
    ItemRepository fillItemRepository(ItemRepository itemRepository) {
        Item laptop = new Item("laptop", BigDecimal.TEN, "laptopHash");
        Item torch = new Item("torch", BigDecimal.ONE, "torchHash");
        Item pc = new Item("pc", BigDecimal.TEN, "pcHash");
        List<Item> itemList = new ArrayList<>(List.of(laptop, torch, pc));
        itemRepository.saveAll(itemList);
        return itemRepository;
    }

    @Bean
    DiscountCardRepository fillDiscountCardRepository(DiscountCardRepository discountCardRepository) {
        DiscountCard laptopDiscard = new DiscountCard("laptopCard", "laptop", TypeOfDiscount.CONSTANT, BigDecimal.valueOf(5));
        DiscountCard pcDiscard = new DiscountCard("pcCard", "pc", TypeOfDiscount.PERCENT, BigDecimal.valueOf(0.5));
        discountCardRepository.save(laptopDiscard);
        discountCardRepository.save(pcDiscard);
        return discountCardRepository;
    }
}