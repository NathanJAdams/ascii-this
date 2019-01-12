function getRepos(provider, user, success, error) {
    var url = provider.usersUrlPrefix() + user + provider.usersUrlSuffix();
    $.ajax({
        url,
        success: function(repos) {
            success(provider.reposFunction(repos));
        },
        error
    });
}

function getBranches(provider, user, repo, success, error) {
    var url = provider.branchesUrlPrefix() + user + provider.branchesUrlRepoPrefix() + repo + provider.branchesUrlSuffix();
    $.ajax({
      url,
        success: function(branches) {
            var branchArray = provider.branchesFunction()(branches);
            success(branchArray);
        },
      error
    });
}

function requestDiff(provider, user, repo, branch, success, error) {
    var url = 'https://qcpg7x18of.execute-api.eu-west-1.amazonaws.com/prod';
    var body = {
        provider,
        user,
        repo,
        branch
    };
    $.ajax({
        method: 'POST',
        url,
        body,
        success,
        error
    });
}

function getDiff(id, success, error) {
    var url = 'https://ac1sxokozd.execute-api.eu-west-1.amazonaws.com/prod';
    var body = { id };
    $.ajax({
      method: 'POST',
      url,
      body,
      success,
      error
    });
}
