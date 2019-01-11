function SignupViewModel() {
    var self = this;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.signup = function() {
        console.log('sign up');
        console.log(self.email());
        console.log(self.password());
    };
}
