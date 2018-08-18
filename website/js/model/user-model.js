function UserModel() {
    var self = this;
    this.isSignedUp = ko.observable(false);
    this.isLoggedIn = ko.observable(false);
    this.accessToken;
    this.signup = function(email, password, onSuccess, onError) {
        if (!UserModel.preCheck(email, password)) {
            return false;
        }
        // TODO can attribute be removed
        var emailAttributeData = {
            Name : 'email',
            Value : email
        };
        var emailAttribute = new AmazonCognitoIdentity.CognitoUserAttribute(emailAttributeData);
        var attributeList = [];
        attributeList.push(emailAttribute);
        var _onSuccess = function(result) {
            self.isSignedUp(true);
            if (onSuccess) {
                onSuccess(result);
            }
        };
        var resultHandler = createResultHandler(_onSuccess, onError);
        UserModel.userPool.signUp(email, password, attributeList, null, resultHandler);
    };
    this.login = function(email, password, onSuccess, onError) {
        if (!UserModel.preCheck(email, password)) {
            return false;
        }
        var cognitoUser = UserModel.createCognitoUser(email);
        var authenticationData = {
            Username: email,
            Password: password
        };
        var authenticationDetails = new AmazonCognitoIdentity.AuthenticationDetails(authenticationData);
        var resultHandler = {
            onSuccess: function(result) {
                console.log(result);
                console.log('Successful login');
                self.accessToken = result.accessToken;
                self.isSignedUp(true);
                self.isLoggedIn(true);
                if (onSuccess) {
                    onSuccess(result);
                }
            },
            onFailure: function(error) {
                console.log('Failed to login:' + error);
                if (onError) {
                    onError(error);
                }
            }
        };
        cognitoUser.authenticateUser(authenticationDetails, resultHandler);
    };
    this.changePassword = function(oldPassword, newPassword, onSuccess, onError) {
        var cognitoUser = UserModel.userPool.getCurrentUser();
        if (cognitoUser == null) {
            console.log('User must be logged in to change password');
            return;
        }
        if (!UserModel.passwordRegex.test(oldPassword)) {
            return false;
        }
        if (oldPassword === newPassword) {
            console.log('New password must be different to old password');
            return false;
        }
        var resultHandler = createResultHandler(onSuccess, onError);
        cognitoUser.changePassword(oldPassword, newPassword, resultHandler);
    };
    this.logout = function(onSuccess) {
        var cognitoUser = UserModel.userPool.getCurrentUser();
        if (cognitoUser != null) {
            cognitoUser.signOut();
        }
        self.isLoggedIn(false);
        if (onSuccess) {
            onSuccess();
        }
    };
    // TODO
//    this.globalLogout = function(onSuccess, onError) {
//        var cognitoUser = UserModel.userPool.getCurrentUser();
//        if (cognitoUser == null) {
//            self.isLoggedIn(false);
//        } else {
//            cognitoUser.getSession(function(error, session) {
//                if (error) {
//                    if (onError) {
//                        onError(error);
//                    }
//                } else {
//                    console.log('session validity: ' + session.isValid());
//                    var params = {
//                        AccessToken: self.accessToken
//                    };
//                    cognitoUser.globalSignOut();
//                    self.isLoggedIn(false);
//                    if (onSuccess) {
//                        onSuccess(session);
//                    }
//                }
//            });
//        }
//    };
    this.deleteUser = function(email, password, onSuccess, onError) {
        var cognitoUser = UserModel.createCognitoUser(email);
        var resultHandler = createResultHandler(onSuccess, onError);
        cognitoUser.deleteUser(resultHandler);
        self.isLoggedIn(false);
        self.isSignedUp(false);
    };
};
// only need a simple email check to ensure no obvious mistakes
Object.defineProperty(UserModel, 'emailRegex', { value: /^\S+@\S+$/ });
Object.defineProperty(UserModel, 'passwordRegex', { value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/ });
Object.defineProperty(UserModel, 'preCheck', { value:
    function(email, password) {
        if (!UserModel.emailRegex.test(email)) {
            console.log('Invalid email')
            return false;
        }
        if (!UserModel.passwordRegex.test(password)) {
            console.log('Invalid password')
            return false;
        }
        console.log('Email and Password passed pre checks');
        return true;
    }
});

Object.defineProperty(UserModel, 'clientId', { value: '1f63er20154oqhllurcb4qs9cn' });
Object.defineProperty(UserModel, 'userPoolData', { value: { UserPoolId: 'eu-west-1_eU9ClkneX', ClientId: UserModel.clientId } });
Object.defineProperty(UserModel, 'userPool', { value: new AmazonCognitoIdentity.CognitoUserPool(UserModel.userPoolData) });
Object.defineProperty(UserModel, 'createCognitoUser', { value:
    function(email) {
        var userData = {
            Username: email,
            Pool: UserModel.userPool
        };
        return new AmazonCognitoIdentity.CognitoUser(userData);
    }
});
Object.defineProperty(UserModel, 'createResultHandler', { value:
    function(onSuccess, onError) {
        return function(error, result) {
            if (error) {
                if (onError) {
                    onError(error);
                }
            } else {
                if (onSuccess) {
                    onSuccess(result);
                }
            }
        }
    }
});
