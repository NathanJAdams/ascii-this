package clean.website.response;

import java.util.List;

public class RepoBranchesResponse {
    private final boolean isValid;
    private final List<RepoBranchResponse> repoBranches;

    public RepoBranchesResponse(boolean isValid, List<RepoBranchResponse> repoBranches) {
        this.isValid = isValid;
        this.repoBranches = repoBranches;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<RepoBranchResponse> getRepoBranches() {
        return repoBranches;
    }
}
