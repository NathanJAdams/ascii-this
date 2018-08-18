function AccountViewModel(user) {
    var self = this;
    this.user = user;
    this.logout = ko.observable(new LogoutViewModel(user));
    this.tokens = ko.observable(new TokensViewModel(user));
    this.repos = ko.observable(new ReposViewModel(user));
}
