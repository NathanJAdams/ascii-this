package com.repocleaner.clean.website.repository;

import com.repocleaner.clean.website.domain.TransformationEntity;

import java.util.UUID;

public interface TransformationRepository extends AddOrGetRepository<TransformationEntity, UUID, TransformationRepository> {
    TransformationEntity findOneByBranchIdAndStartedIsNull(long branchId);
}
