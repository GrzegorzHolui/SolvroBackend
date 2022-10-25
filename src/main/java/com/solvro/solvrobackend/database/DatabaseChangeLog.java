package database;

import Repository.BasketRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import entity.Basket;
import entity.BasketItem;
import entity.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "1")
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "seedDatabase", author = "bartlomiej.kalka")
    public void seedDatabase(BasketRepository ticketRepository) {
        System.out.println("TESTUJEMY");
        ticketRepository.save(
                new Basket(new ArrayList<>
                        (List.of(new BasketItem(new Item("laptop", BigDecimal.ONE), 1)))));
    }

//    @ChangeSet(order = "002", id = "seedDatabase2", author = "bartlomiej.kalka")
//    public void seedDatabase2(BasketRepository ticketRepository) {
//        System.out.println("TESTUJEMY");
//        ticketRepository.save(new Ticket("asdasd", List.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()));
//    }
}
