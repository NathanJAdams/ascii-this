package com.repocleaner.clean.website.service;

import com.repocleaner.clean.website.repository.AddOrGetRepository;

import java.util.function.BiFunction;

public interface CreateIfAbsentService<R extends AddOrGetRepository<E, ?, ?>, E> {
    default E getOrCreateIfAbsent(R repository, E entity, BiFunction<R, E, E> getFromRepositoryFunction, boolean createIfAbsent) {
        if (createIfAbsent) {
            return null;//repository.addOrGet(entity, r -> getFromRepositoryFunction.apply(repository, entity));
        } else {
            return getFromRepositoryFunction.apply(repository, entity);
        }
    }
}
