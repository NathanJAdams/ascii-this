function SignupViewModel(user) {
    var self = this;
    this.user = user;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.confirmedPassword = ko.observable('');
    this.isSignedUp = ko.computed(function() {
        return self.user.isSignedUp();
    });
    this.signup = function() {
        var password = self.password();
        var confirmedPassword = self.confirmedPassword();
        if (password !== confirmedPassword) {
            console.log('Passwords must match');
            return false;
        }
        self.user.signup(password, confirmedPassword);
    };
};
