function LoginViewModel() {
    var self = this;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.login = function() {
        console.log('log in');
        console.log("email"+self.email());
        console.log("password"+self.password());
    };
}
