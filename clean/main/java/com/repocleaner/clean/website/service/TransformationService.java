package com.repocleaner.clean.website.service;

import com.repocleaner.clean.website.domain.BranchEntity;
import com.repocleaner.clean.website.domain.TransformationEntity;
import com.repocleaner.clean.website.domain.TransformationOrigin;
import com.repocleaner.clean.website.repository.TransformationRepository;
import com.repocleaner.clean.website.requests.TransformationRequest;
import com.repocleaner.clean.website.response.TransformationResponse;

import java.util.List;
import java.util.UUID;

public class TransformationService implements CreateIfAbsentService<TransformationRepository, TransformationEntity> {
    private final BranchService branchService;
    private final TransformationRepository transformationRepository;

    public TransformationService(BranchService branchService, TransformationRepository transformationRepository) {
        this.branchService = branchService;
        this.transformationRepository = transformationRepository;
    }

    public boolean add(List<TransformationRequest> transformationRequests) {
        // TODO
//        List<TransformationEntity> transformationEntities = transformationRequests.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toList());
//        List<TransformationEntity> saved = transformationRepository.save(transformationEntities);
//        return (saved != null) && (saved.size() == transformationEntities.size());
        return false;
    }

    public TransformationResponse getFromUuid(UUID id) {
        TransformationEntity transformation = null;//transformationRepository.findOne(id);
        boolean success = (transformation != null);
        boolean isStarted = false;
        boolean isCompleted = false;
        String gitDiff = null;
        if (success) {
            isStarted = (transformation.getStarted() != null);
            isCompleted = (transformation.getCompleted() != null);
            gitDiff = transformation.getGitDiff();
        }
        return new TransformationResponse(success, isStarted, isCompleted, gitDiff);
    }

    public UUID get(String repoHostName, String userName, String repoName, String branchName, TransformationOrigin origin) {
        BranchEntity branch = branchService.get(repoHostName, userName, repoName, branchName, false);
        if (branch == null) {
            return null;
        }
        TransformationEntity transformation = new TransformationEntity();
        transformation.setBranch(branch);
        transformation.setOrigin(origin);
        transformation = getOrCreateIfAbsent(transformationRepository, transformation, this::getFromRepository, true);
        return (transformation == null)
                ? null
                : transformation.getId();
    }

    private TransformationEntity getFromRepository(TransformationRepository repository, TransformationEntity entity) {
        return repository.findOneByBranchIdAndStartedIsNull(entity.getBranch().getId());
    }

    // TODO
//    private TransformationEntity toEntity(TransformationRequest transformationRequest) {
//
//        String repoHostName = transformationRequest.getRepoHostName();
//        String userName = transformationRequest.getUserName();
//        String repoName = transformationRequest.getRepoName();
//        String branchName = transformationRequest.getBranchName();
//        BranchEntity branchEntity = branchService.get(repoHostName,userName,repoName,branchName,false);
//        String gitDiff = transformationRequest.getGitDiff();
//        TransformationEntity transformationEntity = new TransformationEntity();
//        transformationEntity.setBranch(branchEntity);
//        transformationEntity.setCompleted(LocalDateTime.now());
//        transformationEntity.setGitDiff(gitDiff);
//        transformationEntity.set
//    }
}
