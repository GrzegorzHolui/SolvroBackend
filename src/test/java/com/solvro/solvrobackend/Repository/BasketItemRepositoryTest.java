package Repository;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.entity.Basket;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class BasketItemRepositoryTest implements BasketRepository {

    List<Basket> basketList;

    public BasketItemRepositoryTest(List<Basket> basketList) {
        this.basketList = basketList;
    }

    @Override
    public Optional<Basket> findByBasketId(UUID id) {
        return basketList.stream().filter(basket -> basket.getBasketId().equals(id)).findFirst();
    }

    @Override
    public Basket save(Basket basketToSave) {
        this.basketList.add(basketToSave);
        return basketToSave;
    }


    @Override
    public <S extends Basket> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Basket> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<Basket> findAll() {
        return null;
    }

    @Override
    public Iterable<Basket> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(Basket entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Basket> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Basket> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Basket> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Basket> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Basket> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Basket> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Basket> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Basket> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Basket> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Basket> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Basket> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Basket, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}