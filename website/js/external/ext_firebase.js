function extFirebase() {
    self = this;
    this.config = {
        apiKey: "AIzaSyDRY6xLtjJQTHAToMsvIMm3oyT0Rg-dSzo",
        authDomain: "repocleaner-db.firebaseapp.com",
        databaseURL: "https://repocleaner-db.firebaseio.com",
        projectId: "repocleaner-db",
        storageBucket: "repocleaner-db.appspot.com",
        messagingSenderId: "622418851647"
    };
    this.app = firebase.initializeApp(config);
    this.createUser = function(email, password) {
        self.app
            .auth()
            .createUserWithEmailAndPassword(email, password)
            .catch(function(error) {
                 var errorCode = error.code;
                 var errorMessage = error.message;
                 console.log(errorCode);
                 console.log(errorMessage);
            });
    };
    this.signIn = function(email, password) {
        self.app
            .auth()
            .signInWithEmailAndPassword(email, password)
            .catch(function(error) {
                 var errorCode = error.code;
                 var errorMessage = error.message;
                 console.log(errorCode);
                 console.log(errorMessage);
            });
    };
    this.
};
