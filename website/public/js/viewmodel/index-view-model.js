function IndexViewModel() {
    var self = this;
    this.login = ko.observable(new LoginViewModel());
    this.signup = ko.observable(new SignupViewModel());
    var github = {
        name: 'GitHub',
        usersUrlPrefix: 'https://api.github.com/users/',
        usersUrlSuffix: '/repos',
        reposFunction: function(repos) { return repos; },
        branchesUrlPrefix: 'https://api.github.com/repos/',
        branchesUrlRepoPrefix: '/',
        branchesUrlSuffix: '/branches',
        branchesFunction: function(branches) { return branches; },
    };
    var bitbucket = {
        name: 'BitBucket',
        usersUrlPrefix: 'https://api.bitbucket.org/2.0/repositories/',
        usersUrlSuffix: '',
        reposFunction: function(repos) { return repos.values; },
        branchesUrlPrefix: 'https://api.bitbucket.org/2.0/repositories/',
        branchesUrlRepoPrefix: '/',
        branchesUrlSuffix: '/refs/branches',
        branchesFunction: function(branches) {
            var array = [];
            for (var index in branches.values) {
                var branch = branches.values[index];
                array.push(branch);
            }
            console.log(array);
            return array;
        },
    };
    var exampleModel = new ExampleViewModel();
    var githubModel = new ProviderModel(github);
    var bitbucketModel = new ProviderModel(bitbucket);
    exampleModel.providers().push(githubModel);
    exampleModel.providers().push(bitbucketModel);
    exampleModel.provider(bitbucketModel);
    this.example = ko.observable(exampleModel);
}
