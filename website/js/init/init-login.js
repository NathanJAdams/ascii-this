applyBindings();

function applyBindings() {
    var viewModel = new LoginViewModel();
    ko.applyBindings(viewModel);
}
