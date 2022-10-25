package Repository;

import entity.BasketItem;
import entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ItemRepository extends MongoRepository<Item, UUID> {
    Optional<Item> findByProductId(UUID productId);
}
