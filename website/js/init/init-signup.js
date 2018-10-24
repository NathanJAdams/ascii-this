applyBindings();

function applyBindings() {
    var viewModel = new SignupViewModel();
    ko.applyBindings(viewModel);
}
