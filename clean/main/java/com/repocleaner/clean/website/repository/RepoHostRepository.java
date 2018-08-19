package com.repocleaner.clean.website.repository;

import com.repocleaner.clean.website.domain.RepoHostEntity;

public interface RepoHostRepository extends AddOrGetRepository<RepoHostEntity, Integer, RepoHostRepository> {
    RepoHostEntity findOneByName(String name);
}
