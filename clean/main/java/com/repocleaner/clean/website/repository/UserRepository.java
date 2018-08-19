package com.repocleaner.clean.website.repository;

import com.repocleaner.clean.website.domain.UserEntity;

public interface UserRepository extends AddOrGetRepository<UserEntity, Integer, UserRepository> {
    UserEntity findOneByRepoHostIdAndName(long repoHostId, String userName);

//    @Modifying
//    @Query("UPDATE UserEntity u SET u.tokens = ?1 WHERE u.tokens < ?1")
//    @Transactional
    void updateTokensLessThan(long tokens);
}
