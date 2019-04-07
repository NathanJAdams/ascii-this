function ExampleViewModel() {
    var self = this;

    this.spinnerVisibility = ko.observable(false);
    this.providers = ko.observableArray();
    this.provider = ko.observable(null);
    this.user = ko.observable(null);
    this.repos = ko.observableArray();
    this.repo = ko.observable(null);
    this.branches = ko.observableArray();
    this.branch = ko.observable(null);
    this.isValidUser = ko.observable(false);
    this.diffId = ko.observable(null);
    this.checkDiffId = ko.observable(null);
    this.diff = new DiffViewModel();
    this.tryEnabled = ko.observable(true);

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
    this.tryVisibility = ko.computed(function() {
        return (self.branch() != null);
    });

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
    this.checkDiff = function() {
        var onSuccess = (diff, textStatus, xhr) => {
            if (xhr.status == 200) {
                clearInterval(self.checkDiffId());
                self.diffId(null);
                self.checkDiffId(null);
                self.diff.update(diff);
                self.tryEnabled(true);
                self.spinnerVisibility(false);
            }
        };
        var onError = (e) => {
            console.log(e);
        };
        var diff = getDiff(self.diffId(), onSuccess, onError);
    };
    this.tryExample = function() {
        self.tryEnabled(false);
        self.spinnerVisibility(true);
        var currentProvider = self.provider().name();
        var currentUser = self.user();
        var currentRepo = self.repo().name();
        var currentBranch = self.branch().name();
        var onSuccess = result => {
            var id = result.id;
            self.diffId(id);
            var checkDiffIdVal = setInterval(self.checkDiff, 5000);
            self.checkDiffId(checkDiffIdVal);
        };
        var onError = (e) => {
            console.log(e);
            self.tryEnabled(true);
        };
        requestWeb(currentProvider, currentUser, currentRepo, currentBranch, onSuccess, onError);
    };
}
