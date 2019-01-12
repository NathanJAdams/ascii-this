applyBindings();

function applyBindings() {
    var viewModel = new LandingViewModel();
    ko.applyBindings(viewModel);
}
