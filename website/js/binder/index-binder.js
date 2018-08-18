var user = new UserModel();
var viewModel = new IndexViewModel(user);
ko.applyBindings(viewModel);
