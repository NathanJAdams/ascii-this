function firebaseSignup(email, password, onSuccess) {
    if (!isValidCredentials(email, password)) {
        return false;
    }
    firebase.auth()
        .createUserWithEmailAndPassword(email, password)
        .then(function() {
            firebase.auth()
                .currentUser.sendEmailVerification()
            .then(function() {
                window.localStorage.setItem('user-signup-email', email);
                if(onSuccess !== null) {
                    onSuccess();
                }
            }, errorHandler);
        })
        .catch(errorHandler);
}
function firebaseCompleteSignup(onSuccess) {
    var email = window.localStorage.getItem('user-signup-email');
    while (!email) {
        email = window.prompt('Please enter your email to complete the sign up process');
    }
    firebase.auth()
        .signInWithEmailLink(email, window.location.href)
        .then(function(result) {
            window.localStorage.removeItem('user-signup-email', email);
            if(onSuccess !== null) {
                onSuccess();
            }
        })
        .catch(errorHandler);
}
function firebaseLogin(email, password, onSuccess) {
    if (!isValidCredentials(email, password)) {
        return false;
    }
    firebase.auth()
        .signInWithEmailAndPassword(email, password)
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        })
        .catch(errorHandler);
}
function firebaseChangePassword(oldPassword, newPassword, onSuccess) {
    if (!isValidPasswordChange(oldPassword, newPassword)) {
        return false;
    }
    firebase.auth()
        .currentUser.updatePassword(newPassword)
        .then(function() {
            console.log('Successfully changed password')
            if(onSuccess !== null) {
                onSuccess();
            }
        })
        .catch(errorHandler);
}
function firebaseLogout(email, password, onSuccess) {
    if (!isValidCredentials(email, password)) {
        return false;
    }
    firebase.auth()
        .signOut()
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        })
        .catch(errorHandler);
}
function firebaseDeleteUser(onSuccess) {
    firebase.auth()
        .currentUser.delete()
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        })
        .catch(errorHandler);
}
