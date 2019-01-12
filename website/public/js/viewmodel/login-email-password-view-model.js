function LoginEmailPasswordViewModel() {
    var self = this;
    this.email = ko.observable('');
    this.password = ko.observable('');
    this.login = function() {
        console.log('logging in');
        var onSuccess = function() {
            redirect('account');
        };
        firebaseLoginEmailPassword(self.email(), self.password(), onSuccess);
    };
};
