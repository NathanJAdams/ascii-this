applyBindings();

function applyBindings() {
    var viewModel = new LoginEmailPasswordViewModel();
    ko.applyBindings(viewModel);
}
