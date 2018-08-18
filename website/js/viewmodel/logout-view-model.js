function LogoutViewModel(user) {
    var self = this;
    this.user = user;
    this.isLoggedIn = ko.computed(function() {
        return self.user.isLoggedIn();
    });
    this.logout = function() {
        self.user.logout();
    };
    // TODO
//    this.logoutAllDevices = function() {
//        self.user.globalLogout();
//    };
}
