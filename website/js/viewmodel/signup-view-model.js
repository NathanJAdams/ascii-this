function SignupViewModel() {
    var self = this;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.confirmedPassword = ko.observable('');
    this.isEmailValid = ko.computed(function() {
        return isValidEmail(self.email());
    });
    this.isPasswordValidLower = ko.computed(function() {
        return isValidPasswordLower(self.password());
    });
    this.isPasswordValidUpper = ko.computed(function() {
        return isValidPasswordUpper(self.password());
    });
    this.isPasswordValidNumbers = ko.computed(function() {
        return isValidPasswordNumbers(self.password());
    });
    this.isPasswordValidMinChars = ko.computed(function() {
        return isValidPasswordMinChars(self.password());
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
        var onSuccess = function() {
            alert('Check your email');
        };
        firebaseSignup(self.email(), _password, onSuccess);
    };
};
