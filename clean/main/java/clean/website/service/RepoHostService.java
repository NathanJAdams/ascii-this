package clean.website.service;

import clean.website.domain.RepoHostEntity;
import clean.website.repository.RepoHostRepository;

public class RepoHostService {
    private final RepoHostRepository repoHostRepository;

    public RepoHostService(RepoHostRepository repoHostRepository) {
        this.repoHostRepository = repoHostRepository;
    }

    public RepoHostEntity get(String repoHostName) {
        return repoHostRepository.findOneByName(repoHostName);
    }

    public void add(String repoHostName) {
        RepoHostEntity repoHost = new RepoHostEntity();
        repoHost.setName(repoHostName);
//        repoHostRepository.save(repoHost);
    }
}
