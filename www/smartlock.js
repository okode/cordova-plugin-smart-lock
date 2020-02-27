/*global cordova */

var Smartlock = function() {
};

// Plugin Errors
Smartlock.prototype.SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND = -100
Smartlock.prototype.SMARTLOCK__REQUEST__DIALOG_CANCELLED = -101

Smartlock.prototype.SMARTLOCK__SAVE = -200
Smartlock.prototype.SMARTLOCK__SAVE__BAD_REQUEST = -201

Smartlock.prototype.SMARTLOCK__DELETE = -300

Smartlock.prototype.SMARTLOCK__DISABLE_AUTO_SIGN_IN = -400

Smartlock.prototype.SMARTLOCK__COMMON__UNKOWN = -500
Smartlock.prototype.SMARTLOCK__COMMON__CONCURRENT_NOT_ALLOWED = -501
Smartlock.prototype.SMARTLOCK__COMMON__GOOGLE_API_UNAVAILABLE = -502
Smartlock.prototype.SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL = -503

Smartlock.prototype.request = function () {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (credential) { resolve(credential); },
      function (error) { reject(error); },
      "Smartlock",
      "request",
      []
    );
  });
};

Smartlock.prototype.save = function (params) {
  return new Promise(function(resolve, reject) {
    cordova.exec(
      function() { resolve(true); },
      function(error) { reject(error); },
      "Smartlock",
      "save",
      [params]
    );
  });
};

Smartlock.prototype.delete = function (params) {
  return new Promise(function(resolve, reject) {
    cordova.exec(
      function() { resolve(true); },
      function(error) { reject(error); },
      "Smartlock",
      "delete",
      [params]
    );
  });
};

Smartlock.prototype.disableAutoSignIn = function () {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function () { resolve(true); },
      function (error) { reject(error); },
      "Smartlock",
      "disableAutoSignIn",
      []
    );
  });
};

module.exports = new Smartlock();
