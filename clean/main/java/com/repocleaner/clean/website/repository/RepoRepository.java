package com.repocleaner.clean.website.repository;

import com.repocleaner.clean.website.domain.RepoEntity;

public interface RepoRepository extends AddOrGetRepository<RepoEntity, Long, RepoRepository> {
    RepoEntity findOneByOwnerIdAndName(int ownerId, String repoName);
}
