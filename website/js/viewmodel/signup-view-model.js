function SignupViewModel(user) {
    var self = this;
    this.user = user;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.confirmedPassword = ko.observable('');
    this.isEmailValid = ko.computed(function() {
        return UserModel.isValidEmail(self.email());
    });
    this.isPasswordValidLower = ko.computed(function() {
        return UserModel.isValidPasswordLower(self.password());
    });
    this.isPasswordValidUpper = ko.computed(function() {
        return UserModel.isValidPasswordUpper(self.password());
    });
    this.isPasswordValidNumbers = ko.computed(function() {
        return UserModel.isValidPasswordNumbers(self.password());
    });
    this.isPasswordValidMinChars = ko.computed(function() {
        return UserModel.isValidPasswordMinChars(self.password());
    });
    this.isPasswordMatch = ko.computed(function() {
        return self.password() === self.confirmedPassword();
    });
    this.signup = function() {
        var _password = self.password();
        if (_password !== self.confirmedPassword()) {
            console.log('Passwords must match');
            return false;
        }
        self.user.signup(self.email(), _password);
    };
};
