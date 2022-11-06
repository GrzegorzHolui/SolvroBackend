package com.solvro.solvrobackend.Repository;

import com.solvro.solvrobackend.model.DiscountCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountCardRepository extends MongoRepository<DiscountCard, UUID> {

    Optional<DiscountCard> findFirstByCardHash(String hash);
}
