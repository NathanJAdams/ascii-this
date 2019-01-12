function isValidEmail(email) {
    return /^\S+@\S+$/.test(email);
}
function isValidPasswordLower(password) {
    return /^(?=.*[a-z])/.test(password);
}
function isValidPasswordUpper(password) {
    return /^(?=.*[A-Z])/.test(password);
}
function isValidPasswordNumbers(password) {
    return /^(?=.*[0-9])/.test(password);
}
function isValidPasswordMinChars(password) {
    return /^(?=.{8,})/.test(password);
}
function isValidPassword(password) {
    return isValidPasswordLower(password)
        && isValidPasswordUpper(password)
        && isValidPasswordNumbers(password)
        && isValidPasswordMinChars(password);
}
function isValidCredentials(email, password) {
    return isValidEmail(email)
        && isValidPassword(password);
}
function isValidPasswordChange(oldPassword, newPassword) {
    return (oldPassword !== newPassword)
        && isValidPassword(oldPassword)
        && isValidPassword(newPassword);
}
