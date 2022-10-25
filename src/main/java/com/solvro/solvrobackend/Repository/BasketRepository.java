package Repository;

import entity.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BasketRepository extends MongoRepository<Basket, UUID> {
    Optional<Basket> findByBasketId(UUID id);

    Basket save(Basket basketToSave);
}
