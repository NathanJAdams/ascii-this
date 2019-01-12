function UserViewModel() {
    var self = this;
    this.id = ko.computed(function() {
        return firebaseUserProperty(user => user.uid);
    });
    this.email = ko.computed(function() {
        return firebaseUserProperty(user => user.email);
    });
    this.name = ko.computed(function() {
        return firebaseUserProperty(user => user.displayName);
    });
    this.isEmailVerified = ko.computed(function() {
        return firebaseUserProperty(user => user.emailVerified);
    });
    this.logout = ko.observable(new LogoutViewModel());
    this.credits = ko.observable(new CreditsViewModel());
    this.repos = ko.observable(new ReposViewModel());
}
