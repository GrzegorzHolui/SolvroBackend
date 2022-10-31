package com.solvro.solvrobackend.database;

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
    ItemRepository fillItemRepoistory(ItemRepository itemRepository) {
        Item laptop = new Item("laptop", BigDecimal.TEN, "laptophash");
        Item torch = new Item("torch", BigDecimal.ONE, "53125dcc-5900-11ed-9b6a-0242ac120002");
        Item pc = new Item("pc", BigDecimal.TEN, "58e67602-5900-11ed-9b6a-0242ac120002");
        List<Item> itemList = new ArrayList<>(List.of(laptop, torch, pc));
        itemRepository.saveAll(itemList);
        System.out.println(itemRepository.findFirstByProductHash("laptophash"));
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