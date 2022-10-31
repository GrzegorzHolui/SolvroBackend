package com.solvro.solvrobackend.Repository;

import com.solvro.solvrobackend.model.DiscountCard;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class DiscountCardRepositoryTest implements DiscountCardRepository {
    private List<DiscountCard> list;

    public DiscountCardRepositoryTest() {
    }

    public DiscountCardRepositoryTest(List<DiscountCard> list) {
        this.list = list;
    }

    @Override
    public Optional<DiscountCard> findByCardHash(String hash) {
        return list.stream().filter(discountCard -> discountCard.getCardHash().equals(hash)).findFirst();
    }

    @Override
    public <S extends DiscountCard> S save(S entity) {
        return null;
    }

    @Override
    public <S extends DiscountCard> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<DiscountCard> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<DiscountCard> findAll() {
        return null;
    }

    @Override
    public Iterable<DiscountCard> findAllById(Iterable<UUID> uuids) {
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
    public void delete(DiscountCard entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends DiscountCard> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<DiscountCard> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<DiscountCard> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DiscountCard> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends DiscountCard> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends DiscountCard> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DiscountCard> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends DiscountCard> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends DiscountCard> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DiscountCard> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DiscountCard> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DiscountCard, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
