// email password
function firebaseSignupEmailPassword(email, password, onSuccess) {
    if (!isValidCredentials(email, password)) {
        return false;
    }
    firebase.auth()
        .createUserWithEmailAndPassword(email, password)
        .then(function() {
            firebase.auth()
                .currentUser.sendEmailVerification()
            .then(function() {
                if(onSuccess !== null) {
                    onSuccess();
                }
            }, errorHandler);
        }).catch(errorHandler);
}
function firebaseLoginEmailPassword(email, password, onSuccess) {
    if (!isValidCredentials(email, password)) {
        return false;
    }
    firebase.auth()
        .signInWithEmailAndPassword(email, password)
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        }).catch(errorHandler);
}

// email link
function firebaseSendLoginEmailLink(email, redirectPage, onSuccess) {
    var actionCodeSettings = {
        url: createRedirectUrl(redirectPage),
        handleCodeInApp: true
    };
    firebase.auth()
        .sendSignInLinkToEmail(email, actionCodeSettings)
        .then(function() {
            window.localStorage.setItem('user-signup-email', email);
            if (onSuccess !== null) {
                onSuccess();
            }
        }).catch(errorHandler);
}
function firebaseLoginFromEmailLink(emailGetter, onSuccess) {
    var email = window.localStorage.getItem('user-signup-email');
    while (!email) {
        email = emailGetter();
        // window.prompt('Please enter your email to complete the sign up process');
    }
    firebase.auth()
        .signInWithEmailLink(email, window.location.href)
        .then(function(result) {
            window.localStorage.removeItem('user-signup-email', email);
            if(onSuccess !== null) {
                onSuccess();
            }
        }).catch(errorHandler);
}

function firebaseChangePassword(oldPassword, newPassword, onSuccess) {
    if (!isValidPasswordChange(oldPassword, newPassword)) {
        return false;
    }
    firebase.auth()
        .currentUser.updatePassword(newPassword)
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        }).catch(errorHandler);
}
function firebaseLogout(onSuccess) {
    firebase.auth()
        .signOut()
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        }).catch(errorHandler);
}
function firebaseDeleteUser(onSuccess) {
    firebase.auth()
        .currentUser.delete()
        .then(function() {
            if(onSuccess !== null) {
                onSuccess();
            }
        }).catch(errorHandler);
}
function firebaseUserProperty(propertyGetter) {
    var user = firebase.auth().currentUser;
    return (user === null)
        ? null
        : propertyGetter(user);
}
