package clean.website.repository;

import clean.website.domain.RepoEntity;

public interface RepoRepository extends AddOrGetRepository<RepoEntity, Long, RepoRepository> {
    RepoEntity findOneByOwnerIdAndName(int ownerId, String repoName);
}
