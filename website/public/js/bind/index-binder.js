applyBindings();

function applyBindings() {
    var viewModel = new IndexViewModel();
    ko.applyBindings(viewModel);
}
