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

function requestWeb(provider, user, repo, branch, success, error) {
    var url = 'https://983keykw22.execute-api.eu-west-1.amazonaws.com/prod/request-web';
    var data = {
        provider,
        user,
        repo,
        branch
    };
    data = JSON.stringify(data);
    console.log(data);
    $.ajax({
        type: 'POST',
        url,
        contentType: 'application/json',
        data,
        dataType: 'json',
        success,
        error
    });
}

function getDiff(id, success, error) {
    var url = 'https://hn1vshpp7b.execute-api.eu-west-1.amazonaws.com/prod/retrieve-diff';
    var data = { id };
    data = JSON.stringify(data);
    $.ajax({
        type: 'POST',
        url,
        contentType: 'application/json',
        data,
        dataType: 'json',
        success,
        error
    });
}

function getEncryptedToken(token, success, error) {
    var url = 'https://q18tczrab8.execute-api.eu-west-1.amazonaws.com/prod/encrypt-token';
    var data = { token };
    data = JSON.stringify(data);
    $.ajax({
        type: 'POST',
        url,
        contentType: 'application/json',
        data,
        dataType: 'json',
        success,
        error
    });
}