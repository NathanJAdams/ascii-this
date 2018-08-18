package clean.website.service;

import clean.website.domain.RepoHostEntity;
import clean.website.domain.UserEntity;
import clean.website.repository.UserRepository;

public class UserService implements CreateIfAbsentService<UserRepository, UserEntity> {
    private final RepoHostService repoHostService;
    private final UserRepository userRepository;
    private final long initialTokens;

    public UserService(RepoHostService repoHostService, UserRepository userRepository, /*@Value("${cleaner.tokens.monthly-min}")*/ long initialTokens) {
        this.repoHostService = repoHostService;
        this.userRepository = userRepository;
        this.initialTokens = initialTokens;
    }

    public Integer getId(String repoHostName, String userName, boolean createIfAbsent) {
        UserEntity user = get(repoHostName, userName, createIfAbsent);
        return (user == null)
                ? null
                : user.getId();
    }

    public UserEntity get(String repoHostName, String userName, boolean createIfAbsent) {
        RepoHostEntity repoHost = repoHostService.get(repoHostName);
        if (repoHost == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setRepoHost(repoHost);
        user.setName(userName);
        user.setTokens(initialTokens);
        return getOrCreateIfAbsent(userRepository, user, this::getFromRepository, createIfAbsent);
    }

    private UserEntity getFromRepository(UserRepository repository, UserEntity entity) {
        return repository.findOneByRepoHostIdAndName(entity.getRepoHost().getId(), entity.getName());
    }

    public void replenishTokens() {
        userRepository.updateTokensLessThan(initialTokens);
    }
}
