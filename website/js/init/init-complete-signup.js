completeSignup();

function completeSignup() {
    var onSuccess = function() {
        redirect('account');
    };
    firebaseCompleteSignup(onSuccess);
}
