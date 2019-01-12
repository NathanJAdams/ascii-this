function encryptToken(body, success, error) {
    var url = 'https://3rps4m1fm1.execute-api.eu-west-1.amazonaws.com/default/encrypt-token';
    $.ajax({
      method: 'POST',
      url,
      body,
      success,
      error
    });
}
