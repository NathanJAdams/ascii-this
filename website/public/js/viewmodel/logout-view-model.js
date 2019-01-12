function LogoutViewModel(user) {
    var self = this;
    this.user = user;
    this.isLoggedIn = ko.computed(function() {
        return firebaseUserProperty(user => user.isLoggedIn());
    });
    this.logout = function() {
        firebaseLogout();
    };
    // TODO
//    this.logoutAllDevices = function() {
//        self.user.globalLogout();
//    };
}
