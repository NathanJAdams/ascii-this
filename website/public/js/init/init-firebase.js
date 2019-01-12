initFirebaseApp();

function initFirebaseApp() {
    var firebaseConfig = {
        apiKey: "AIzaSyAfL1MMGLhjZT9X21wdh8lGECA9bugYBAE",
        authDomain: "repo-cleaner-app.firebaseapp.com",
        databaseURL: "https://repo-cleaner-app.firebaseio.com",
        projectId: "repo-cleaner-app",
        storageBucket: "repo-cleaner-app.appspot.com",
        messagingSenderId: "455362055216"
    };
    firebase.initializeApp(firebaseConfig);
}
