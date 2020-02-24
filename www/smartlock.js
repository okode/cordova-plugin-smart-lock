/*global cordova */

var Smartlock = function() {
};

// Plugin Errors
Smartlock.prototype.SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND = -100
Smartlock.prototype.SMARTLOCK__REQUEST__DIALOG_CANCELLED = -101

Smartlock.prototype.SMARTLOCK__SAVE = -200
Smartlock.prototype.SMARTLOCK__SAVE__BAD_REQUEST = -201

Smartlock.prototype.SMARTLOCK__DELETE = -300

Smartlock.prototype.SMARTLOCK__COMMON__UNKOWN = -400
Smartlock.prototype.SMARTLOCK__COMMON__CONCURRENT_NOT_ALLOWED = -401
Smartlock.prototype.SMARTLOCK__COMMON__GOOGLE_API_UNAVAILABLE = -402
Smartlock.prototype.SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL = -403

Smartlock.prototype.request = function () {
  return new Promise((resolve, reject) => {
    cordova.exec(
      credential => { resolve(credential); },
      error => { reject(error); },
      "Smartlock",
      "request",
      []
    );
  });
};

Smartlock.prototype.save = function (params) {
  return new Promise((resolve, reject) => {
    cordova.exec(
      () => { resolve(true); },
      error => { reject(error); },
      "Smartlock",
      "save",
      [params]
    );
  });
};

Smartlock.prototype.delete = function (params) {
  return new Promise((resolve, reject) => {
    cordova.exec(
      () => { resolve(true); },
      error => { reject(error); },
      "Smartlock",
      "delete",
      [params]
    );
  });
};

module.exports = new Smartlock();
