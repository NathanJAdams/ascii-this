function BranchModel(json) {
    var self = this;
    this.name = ko.observable(json.name);
//    this.refName = ko.computed(function() {
//        return self.ref()
//            .replace("refs/heads/", "branch - ")
//            .replace("refs/tags/", "tag - ");
//    });
//    this.isCleaned = ko.observable(true);
//    this.opacity = ko.computed(function() {
//        var isCleaned = self.isCleaned();
//        return isCleaned
//            ? 1.0
//            : 0.5;
//    });
};
