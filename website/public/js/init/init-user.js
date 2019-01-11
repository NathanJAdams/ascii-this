applyBindings();

function applyBindings() {
    var viewModel = new UserViewModel();
    ko.applyBindings(viewModel);
}
