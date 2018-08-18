package clean.website.response;

public class BranchResponse {
    private final String repoHostName;
    private final String userName;
    private final String repoName;
    private final String branchName;

    public BranchResponse(String repoHostName, String userName, String repoName, String branchName) {
        this.repoHostName = repoHostName;
        this.userName = userName;
        this.repoName = repoName;
        this.branchName = branchName;
    }

    public String getRepoHostName() {
        return repoHostName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getBranchName() {
        return branchName;
    }
}
