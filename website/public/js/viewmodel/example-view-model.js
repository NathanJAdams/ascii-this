function ExampleViewModel() {
    var self = this;
    this.providers = ko.observableArray();
    this.provider = ko.observable(null);
    this.user = ko.observable(null);
    this.repos = ko.observableArray();
    this.repo = ko.observable(null);
    this.branches = ko.observableArray();
    this.branch = ko.observable(null);
    this.isValidUser = ko.observable(false);
    this.validityColor = ko.computed(function() {
        return self.isValidUser()
            ? 'lightgreen'
            : 'pink';
    });
    this.updateUser = ko.computed(function() {
        var currentUser = self.user();
        if (currentUser == null || currentUser == '') {
            self.isValidUser(false);
            self.repos([]);
            self.branches([]);
            return;
        }
        var currentProvider = self.provider();
        if (currentProvider != null) {
            getRepos(currentProvider, currentUser, self.setRepos, self.setReposError);
        }
    }).extend({throttle: 1000});
    this.setRepos = function(repos) {
        var repoArray = [];
        self.isValidUser(true);
        repos.forEach(function(repoJson) {
            var repoModel = new RepoModel(repoJson);
            repoArray.push(repoModel);
        });
        self.repos(repoArray);
    };
    this.setReposError = function() {
        self.isValidUser(false);
        self.repos([]);
        self.branches([]);
    };
    this.updateRepo = ko.computed(function() {
        var currentRepo = self.repo();
        if (currentRepo == null) {
            self.branches([]);
        } else {
            var provider = self.provider();
            var user = self.user();
            var repo = currentRepo.name();
            getBranches(provider, user, repo, self.setBranches, self.setBranchesError);
        }
    }).extend({throttle: 1000});
    this.setBranches = function(branches) {
        var branchArray = [];
        branches.forEach(function(branchJson) {
            var branchModel = new BranchModel(branchJson);
            branchArray.push(branchModel);
        });
        self.branches(branchArray);
    };
    this.setBranchesError = function() {
    };
    this.tryVisibility = ko.computed(function() {
        return (self.branch() != null);
    });
    this.tryExample = function() {
        console.log('try example');
        var currentProvider = self.provider().name();
        var currentUser = self.user();
        var currentRepo = self.repo().name();
        var currentBranch = self.branch().name();
        var onSuccess = id => {
            console.log('success ' + id);
        };
        var onError = () => {
            console.log('error');
        };
        requestDiff(currentProvider, currentUser, currentRepo, currentBranch, onSuccess, onError);
        // TODO keep checking for completion
    };
}
