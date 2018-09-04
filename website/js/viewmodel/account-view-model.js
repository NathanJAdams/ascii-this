function AccountViewModel(user) {
    var self = this;
    this.user = user;
    this.logout = ko.observable(new LogoutViewModel(user));
    this.credits = ko.observable(new CreditsViewModel(user));
    this.repos = ko.observable(new ReposViewModel(user));
}
