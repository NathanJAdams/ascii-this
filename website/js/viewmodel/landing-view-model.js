function LandingViewModel(user) {
    var self = this;
    this.user = user;
    this.signup = ko.observable(new SignupViewModel(user));
    this.login = ko.observable(new LoginViewModel(user));
}
