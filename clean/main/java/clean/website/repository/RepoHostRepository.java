package clean.website.repository;

import clean.website.domain.RepoHostEntity;

public interface RepoHostRepository extends AddOrGetRepository<RepoHostEntity, Integer, RepoHostRepository> {
    RepoHostEntity findOneByName(String name);
}
