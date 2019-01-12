function ProviderModel(json) {
    var self = this;
    this.name = ko.observable(json.name);
    this.usersUrlPrefix = ko.observable(json.usersUrlPrefix);
    this.usersUrlSuffix = ko.observable(json.usersUrlSuffix);
    this.reposFunction = json.reposFunction;
    this.branchesUrlPrefix = ko.observable(json.branchesUrlPrefix);
    this.branchesUrlRepoPrefix = ko.observable(json.branchesUrlRepoPrefix);
    this.branchesUrlSuffix = ko.observable(json.branchesUrlSuffix);
    this.branchesFunction = ko.observable(json.branchesFunction);
};
