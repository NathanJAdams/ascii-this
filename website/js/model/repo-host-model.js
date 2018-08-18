function RepoHostModel(json) {
    this.name = ko.observable(json.name);
    this.urlRoot = ko.observable(json.urlRoot);
    this.urlUsers = ko.observable(json.urlUsers);
    this.apiUrlRoot = ko.observable(json.apiUrlRoot);
    this.apiUrlRepos = ko.observable(json.apiUrlRepos);
};
