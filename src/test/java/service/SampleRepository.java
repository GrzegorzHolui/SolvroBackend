package service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.BasketRepositoryTest;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.Repository.ItemRepositoryTest;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.Item;

import java.util.ArrayList;
import java.util.List;

interface SampleRepository {

    default BasketRepository sampleBasketRepository(Basket basket) {
        return new BasketRepositoryTest(new ArrayList<>(List.of(basket)));
    }

    default ItemRepositoryTest sampleItemRepository(Item item) {
        return new ItemRepositoryTest(new ArrayList<>(List.of(item)));
    }

}
