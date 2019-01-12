applyBindings();

function applyBindings() {
    var viewModel = new SignupEmailPasswordViewModel();
    ko.applyBindings(viewModel);
}
