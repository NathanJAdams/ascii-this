initFirebaseApp();

function initFirebaseApp() {
    var firebaseConfig = {
        apiKey: 'AIzaSyDRY6xLtjJQTHAToMsvIMm3oyT0Rg-dSzo',
        authDomain: 'repocleaner-db.firebaseapp.com',
        databaseURL: 'https://repocleaner-db.firebaseio.com',
        projectId: 'repocleaner-db',
        storageBucket: 'repocleaner-db.appspot.com',
        messagingSenderId: '622418851647'
    };
    firebase.initializeApp(firebaseConfig);
}
