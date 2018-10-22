function UserModel() {
    var self = this;
    this.isLoggedIn = ko.observable(false);


    //  http://www.repocleaner.com/?mode=verifyEmail&oobCode=HRHKY5YAzVutrXnkw694fdIjfF7sf7Uw5jWB7OfkqOoAAAFmmQ0y_g&apiKey=AIzaSyDRY6xLtjJQTHAToMsvIMm3oyT0Rg-dSzo&lang=en

    this.signup = function(email, password) {
        if (self.isLoggedIn()) {
            console.log('Already logged in');
            return;
        }
        if (!UserModel.isValidCredentials(email, password)) {
            console.log("Invalid credentials");
            return false;
        }
        firebase.auth()
            .createUserWithEmailAndPassword(email, password)
            .then(function() {
                firebase.auth()
                    .currentUser.sendEmailVerification()
                .then(function() {
                    // email sent
                }, UserModel.errorHandler);
            })
            .catch(UserModel.errorHandler);
    };
    this.login = function(email, password) {
        if (self.isLoggedIn()) {
            console.log('Already logged in');
            return;
        }
        if (!UserModel.isValidCredentials(email, password)) {
            return false;
        }
        firebase.auth()
            .signInWithEmailAndPassword(email, password)
            .then(function() {
                self.isLoggedIn(true);
            })
            .catch(UserModel.errorHandler);
    };
    this.changePassword = function(oldPassword, newPassword) {
        if (!self.isLoggedIn()) {
            console.log('Not logged in');
            return;
        }
        if (!UserModel.isValidPasswordChange(oldPassword, newPassword)) {
            return false;
        }
        firebase.auth()
            .currentUser.updatePassword(newPassword)
            .then(function() {
                console.log('Successfully changed password')
            })
            .catch(UserModel.errorHandler);
    };
    this.logout = function(email, password) {
        if (!self.isLoggedIn()) {
            console.log('Not logged in');
            return;
        }
        if (!UserModel.isValidCredentials(email, password)) {
            return false;
        }
        firebase.auth()
            .signOut()
            .then(function() {
                self.isLoggedIn(false);
            })
            .catch(UserModel.errorHandler);
    };
    this.deleteUser = function() {
        if (!self.isLoggedIn()) {
            console.log('Not logged in');
            return;
        }
        firebase.auth()
            .currentUser.delete()
            .then(function() {
                self.isLoggedIn(false);
            })
            .catch(UserModel.errorHandler);
    };
};
Object.defineProperty(UserModel, 'isValidEmail', { value: function(email) { return /^\S+@\S+$/.test(email); } });
Object.defineProperty(UserModel, 'isValidPasswordLower', { value: function(password) { return /^(?=.*[a-z])/.test(password); } });
Object.defineProperty(UserModel, 'isValidPasswordUpper', { value: function(password) { return /^(?=.*[A-Z])/.test(password); } });
Object.defineProperty(UserModel, 'isValidPasswordNumbers', { value: function(password) { return /^(?=.*[0-9])/.test(password); } });
Object.defineProperty(UserModel, 'isValidPasswordMinChars', { value: function(password) { return /^(?=.{8,})/.test(password); } });
Object.defineProperty(UserModel, 'isValidPassword', { value:
    function(password) {
        console.log("lower" + UserModel.isValidPasswordLower(password));
        console.log("upper" + UserModel.isValidPasswordUpper(password));
        console.log("numbers" + UserModel.isValidPasswordNumbers(password));
        console.log("min chars" + UserModel.isValidPasswordMinChars(password));
        return UserModel.isValidPasswordLower(password)
            && UserModel.isValidPasswordUpper(password)
            && UserModel.isValidPasswordNumbers(password)
            && UserModel.isValidPasswordMinChars(password);
    }
});
Object.defineProperty(UserModel, 'isValidCredentials', { value:
    function(email, password) {
        console.log(UserModel.isValidEmail("email" + email));
        return UserModel.isValidEmail(email)
            && UserModel.isValidPassword(password);
    }
});
Object.defineProperty(UserModel, 'isValidPasswordChange', { value:
    function(oldPassword, newPassword) {
        return oldPassword !== newPassword
            && UserModel.isValidPassword(oldPassword)
            && UserModel.isValidPassword(newPassword);
    }
});
Object.defineProperty(UserModel, 'errorHandler', { value:
    function(error) {
        console.log(error.code);
        console.log(error.message);
    }
});
