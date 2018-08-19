package com.repocleaner.clean.website.service;

import com.repocleaner.clean.website.domain.BranchEntity;
import com.repocleaner.clean.website.domain.RepoEntity;
import com.repocleaner.clean.website.domain.RepoHostEntity;
import com.repocleaner.clean.website.domain.UserEntity;
import com.repocleaner.clean.website.repository.BranchRepository;
import com.repocleaner.clean.website.response.BranchResponse;

import java.time.LocalDateTime;

public class BranchService implements CreateIfAbsentService<BranchRepository, BranchEntity> {
    private final RepoService repoService;
    private final BranchRepository branchRepository;

    public BranchService(RepoService repoService, BranchRepository branchRepository) {
        this.repoService = repoService;
        this.branchRepository = branchRepository;
    }

    public BranchEntity get(String repoHostName, String userName, String repoName, String branchName, boolean createIfAbsent) {
        RepoEntity repo = repoService.get(repoHostName, userName, repoName, createIfAbsent);
        if (repo == null) {
            return null;
        }
        BranchEntity branch = new BranchEntity();
        branch.setRepo(repo);
        branch.setName(branchName);
        return getOrCreateIfAbsent(branchRepository, branch, this::getFromRepository, createIfAbsent);
    }

    private BranchEntity getFromRepository(BranchRepository repository, BranchEntity entity) {
        return repository.findOneByRepoIdAndName(entity.getRepo().getId(), entity.getName());
    }

//    public List<BranchResponse> getNextToBeTransformedBefore(LocalDateTime before, int count) {
//        Pageable pageable = new PageRequest(0, count, new Sort(Sort.Direction.ASC, "NEXT_TRANSFORMATION"));
//        List<BranchEntity> next = branchRepository.findByNextTransformationLessThan(before, pageable);
//        return ((next == null) || next.isEmpty())
//                ? Collections.emptyList()
//                : next.stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//    }

    public LocalDateTime getNextTransformationTime() {
        BranchEntity branch = branchRepository.findFirstOrderByNextTransformation();
        return (branch == null)
                ? LocalDateTime.MAX
                : branch.getNextTransformation();
    }

    private BranchResponse toResponse(BranchEntity branchEntity) {
        String branchName = branchEntity.getName();
        RepoEntity repoEntity = branchEntity.getRepo();
        String repoName = repoEntity.getName();
        UserEntity userEntity = repoEntity.getOwner();
        String userName = userEntity.getName();
        RepoHostEntity repoHostEntity = userEntity.getRepoHost();
        String repoHostName = repoHostEntity.getName();
        return new BranchResponse(repoHostName, userName, repoName, branchName);
    }
}
