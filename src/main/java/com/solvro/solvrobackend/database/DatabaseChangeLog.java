package com.solvro.solvrobackend.database;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.solvro.solvrobackend.entity.Basket;
import com.solvro.solvrobackend.entity.BasketItem;
import com.solvro.solvrobackend.entity.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ChangeLog(order = "1")
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "seedDatabase", author = "bartlomiej.kalka")
    public void seedDatabase(BasketRepository ticketRepository) {
        System.out.println("TESTUJEMY");
        ticketRepository.save(
                new Basket(new ArrayList<>
                        (List.of(new BasketItem(new Item(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"), "laptop", BigDecimal.ONE), 1)))));
    }

//    @ChangeSet(order = "002", id = "seedDatabase2", author = "bartlomiej.kalka")
//    public void seedDatabase2(BasketRepository ticketRepository) {
//        System.out.println("TESTUJEMY");
//        ticketRepository.save(new Ticket("asdasd", List.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()));
//    }
}
