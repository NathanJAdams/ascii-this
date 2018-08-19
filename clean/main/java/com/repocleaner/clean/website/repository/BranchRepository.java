package com.repocleaner.clean.website.repository;

import com.repocleaner.clean.website.domain.BranchEntity;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BranchRepository extends AddOrGetRepository<BranchEntity, UUID, BranchRepository> {
    BranchEntity findOneByRepoIdAndName(long repoId, String name);

    List<BranchEntity> findByNextTransformationLessThan(LocalDateTime localDateTime, Pageable pageable);

    BranchEntity findFirstOrderByNextTransformation();
}
