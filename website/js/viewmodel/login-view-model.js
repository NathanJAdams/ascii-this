function LoginViewModel() {
    var self = this;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.login = function() {
        console.log('logging in');
        var onSuccess = function() {
            redirect('account');
        };
        firebaseLogin(self.email(), self.password(), onSuccess);
    };
};
