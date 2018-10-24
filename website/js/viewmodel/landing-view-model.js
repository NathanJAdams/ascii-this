function LandingViewModel() {
    var self = this;
    this.signup = function() {
        redirect('signup');
    };
    this.login = function() {
        redirect('login');
    };
}
