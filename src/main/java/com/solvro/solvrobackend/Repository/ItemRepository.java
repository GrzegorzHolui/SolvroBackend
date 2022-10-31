package com.solvro.solvrobackend.Repository;

import com.solvro.solvrobackend.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ItemRepository extends MongoRepository<Item, UUID> {
    Optional<Item> findFirstByProductHash(String productHash);
}