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
