function getRepos(repoHost, user, resultFunction) {
    $.ajax({
      url: document.URL + 'repos/' + repoHost + '/' + user,
      success(repos) {
        resultFunction(repos);
      },
      error(e) {
        console.log(e);
      }
    });
}

function getBranches(repoHost, user, repo, resultFunction) {
    $.ajax({
      url: document.URL + 'branches/' + repoHost + '/' + user + '/' + repo,
      success(repos) {
        resultFunction(repos);
      },
      error(e) {
        console.log(e);
      }
    });
}

function encryptToken(token) {
    $.ajax({
      method: 'POST',
      url: 'https://3rps4m1fm1.execute-api.eu-west-1.amazonaws.com/default/encrypt-token',
      body: {
        token
      },
      success(repos) {
        resultFunction(repos);
      },
      error(e) {
        console.log(e);
      }
    });
}
