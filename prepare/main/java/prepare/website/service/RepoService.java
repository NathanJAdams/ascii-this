package clean.website.service;

import clean.website.domain.RepoEntity;
import clean.website.domain.UserEntity;
import clean.website.repository.RepoRepository;

public class RepoService implements CreateIfAbsentService<RepoRepository, RepoEntity> {
    private final UserService userService;
    private final RepoRepository repoRepository;

    public RepoService(UserService userService, RepoRepository repoRepository) {
        this.userService = userService;
        this.repoRepository = repoRepository;
    }

    public RepoEntity get(String repoHostName, String userName, String repoName, boolean createIfAbsent) {
        UserEntity user = userService.get(repoHostName, userName, createIfAbsent);
        if (user == null) {
            return null;
        }
        RepoEntity repo = new RepoEntity();
        repo.setOwner(user);
        repo.setName(repoName);
        return getOrCreateIfAbsent(repoRepository, repo, this::getFromRepository, createIfAbsent);
    }

    private RepoEntity getFromRepository(RepoRepository repository, RepoEntity entity) {
        return repository.findOneByOwnerIdAndName(entity.getOwner().getId(), entity.getName());
    }

    public boolean setAccessToken(String repoHostName, String userName, String repoName, String token) {
        RepoEntity repo = get(repoHostName, userName, repoName, true);
        if (repo == null) {
            return false;
        }
        repo.setApiKey(token);
//        repo = repoRepository.save(repo);
        return (repo != null);
    }
}
