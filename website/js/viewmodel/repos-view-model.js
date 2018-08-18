function ReposViewModel(user) {
    var self = this;
    this.user = user;
    this.repoHosts = ko.observableArray();
    this.repoHosts.push(new RepoHostModel({name: 'GitHub', urlRoot: 'https://github.com'}));
    this.repoHosts.push(new RepoHostModel({name: 'BitBucket', urlRoot: 'https://bitbucket.org'}));
    this.repoHost = ko.observable();
    this.userName = ko.observable();
    this.repoRegexLiteral = ko.observable('');
    this.branchRegexLiteral = ko.observable('');
    this.repos = ko.observableArray();
    this.branches = ko.observableArray();
    this.isValidUser = ko.observable(false);
    this.validityColor = ko.computed(function() {
        return self.isValidUser()
            ? 'lightgreen'
            : ' pink';
    });
    this.setRepoHost = function(repoHost) {
        self.repoHost(repoHost);
    };
    this.repoHostUrlRoot = ko.computed(function() {
        var repoHost = self.repoHost();
        if (repoHost == null) {
            return '';
        }
        return repoHost.urlRoot() + '/';
    });
    this.enterAccountNameText = ko.computed(function() {
        var currentRepoHost = self.repoHost();
        if (currentRepoHost == null) {
            return null;
        }
        return 'Enter your ' + currentRepoHost.name() + ' user account';
    });
    this.updateUser = ko.computed(function() {
        var currentUser = self.userName();
        if (currentUser == null) {
            self.isValidUser(false);
            return;
        }
        var currentRepoHost = self.repoHost();
        if (currentRepoHost != null) {
            var currentRepoHostName = currentRepoHost.name();
            getRepos(currentRepoHostName, currentUser, self.setRepos);
        }
    }).extend({throttle: 500});
    this.setRepos = function(repos) {
        var repoArray = [];
        self.isValidUser(repos.isValid);
        if (repos.isValid) {
            repos.userRepos.forEach(function(repoJson) {
                var repoModel = new RepoModel(repoJson);
                repoArray.push(repoModel);
            });
        }
        self.repos(repoArray);
    };
    this.createRegex = function(literal, replace) {
        if (literal == null) {
            literal = '';
        }
        var newLiteral = literal.replace(/[^a-zA-Z0-9_\-*?\.]/g, '');
        if ((replace != null) && (newLiteral != literal)) {
            literal = newLiteral;
            replace(literal);
        }
        literal = literal.replace(/\./g, '\\.');
        literal = literal.replace(/\?/g, '.');
        literal = literal.replace(/\*/g, '[\\w\\-\\.]*');
        return new RegExp(literal);
    };
    this.repoRegex = ko.computed(function() {
        return self.createRegex(self.repoRegexLiteral(), self.repoRegexLiteral);
    });
    this.updateRepoCleaned = ko.computed(function() {
        var regex = self.repoRegex();
        self.repos().forEach(function(repo) {
            var repoName = repo.name();
            var isMatch = regex.test(repoName);
            repo.isCleaned(isMatch);
        });
    });
    this.branchRegex = ko.computed(function() {
        return self.createRegex(self.branchRegexLiteral(), self.branchRegexLiteral);
    });
    this.updateBranchCleaned = ko.computed(function() {
        var regex = self.branchRegex();
        self.repos().forEach(function(repo) {
            repo.branches().forEach(function(branch) {
                var branchName = branch.name();
                var isMatch = regex.test(branchName);
                branch.isCleaned(isMatch);
            });
        });
    });
    this.testRepo = function(repo) {
        console.log("Testing repo: " + repo.name());
        var currentRepoHost = self.repoHost();
        if (currentRepoHost != null) {
            var currentRepoHostName = currentRepoHost.name();
            var user = self.userName();
            var repoName = repo.name();
            getBranches(currentRepoHostName, user, repoName, self.setBranches);
        }
    };
    this.setBranches = function(branches) {
        var branchArray = [];
        if (branches.isValid) {
            branches.repoBranches.forEach(function(branchJson) {
                var branchModel = new BranchModel(branchJson);
                branchArray.push(branchModel);
            });
        }
        self.branches(branchArray);
    }
    this.testBranch = function(branch) {
        console.log("Testing branch: " + branch.refName());
        // TODO transform branch and show diff
    };
}
