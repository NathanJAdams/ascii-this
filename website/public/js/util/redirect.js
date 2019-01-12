function redirect(page, addToHistory) {
    if (addToHistory === undefined) {
        addToHistory = true;
    }
    var goto = 'https://repo-cleaner-app.firebaseapp.com/' + page;
    if (addToHistory) {
        window.location.href = goto;
    } else {
        window.location.replace(goto);
    }
}
function createRedirectUrl(page) {
    return 'https://repo-cleaner-app.firebaseapp.com/' + page;
}