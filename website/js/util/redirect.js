function redirect(page, addToHistory) {
    if (addToHistory === undefined) {
        addToHistory = true;
    }
    var goto = 'http://repocleaner.com/' + page;
    if (addToHistory) {
        window.location.href = goto;
    } else {
        window.location.replace(goto);
    }
}