function IndexViewModel(user) {
    var self = this;
    this.user = user;
    this.landing = ko.observable(new LandingViewModel(user));
    this.account = ko.observable(new AccountViewModel(user));
}
