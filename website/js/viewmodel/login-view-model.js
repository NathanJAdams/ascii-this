function LoginViewModel(user) {
    var self = this;
    this.user = user;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.isLoggedIn = ko.computed(function() {
        return self.user.isLoggedIn();
    });
    this.login = function() {
        var email = self.email();
        var password = self.password();
        self.user.login(email, password);
    };
};
