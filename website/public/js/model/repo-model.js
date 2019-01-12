function RepoModel(json) {
    var self = this;
    this.name = ko.observable(json.name);
    this.branches = ko.observableArray();
    this.isCleaned = ko.observable(true);
    this.opacity = ko.computed(function() {
        var isCleaned = self.isCleaned();
        return isCleaned
            ? 1.0
            : 0.5;
    });
};
