package com.solvro.solvrobackend.Repository;

import com.solvro.solvrobackend.model.DiscountCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface DiscountCardRepository extends MongoRepository<DiscountCard, UUID> {

    Optional<DiscountCard> findByCardHash(String hash);
}
