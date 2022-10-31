package com.solvro.solvrobackend.Repository;

import com.solvro.solvrobackend.model.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BasketRepository extends MongoRepository<Basket, UUID> {
    Optional<Basket> findFirstByBasketHash(String basketHash);

    Basket save(Basket basketToSave);
}
