function SignupEmailPasswordViewModel() {
    var self = this;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.passwordType = ko.observable('password');
    this.eyeClass = ko.observable('fa-eye');
    this.eyeText = ko.observable('Show');
    this.togglePasswordType = function(item, event) {
        if (self.passwordType() === 'password') {
            self.passwordType('text');
            self.eyeClass('fa-eye-slash');
            self.eyeText('Hide');
        } else {
            self.passwordType('password');
            self.eyeClass('fa-eye');
            self.eyeText('Show');
        }
    };
    this.isEmailValid = ko.computed(function() {
        return isValidEmail(self.email());
    });
    this.isPasswordValidTests = [];
    this.isPasswordValidTests.push({
        text: 'Lower Characters',
        tickDisplay: ko.computed(function(){ return isValidPasswordLower(self.password()) ? 'hidden': 'block'; })
    });
    this.isPasswordValidTests.push({
        text: 'Upper Characters',
        isValid: ko.computed(function(){ return isValidPasswordUpper(self.password()); })
    });
    this.isPasswordValidTests.push({
        text: 'Numbers',
        isValid: ko.computed(function(){ return isValidPasswordNumbers(self.password()); })
    });
    this.isPasswordValidTests.push({
        text: 'Minimum Characters',
        isValid: ko.computed(function(){ return isValidPasswordMinChars(self.password()); })
    });
    this.isValid = ko.computed(function() {
        return isValidCredentials(self.email(), self.password());
    });
    this.signup = function() {
        var onSuccess = function() {
            alert('Check your email');
        };
        firebaseSignupEmailPassword(self.email(), self.password(), onSuccess);
    };
};
