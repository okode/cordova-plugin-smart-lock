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

Smartlock.prototype.request = function (successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "Smartlock",
    "request",
    [{}]
  );
};

Smartlock.prototype.save = function (params, successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "Smartlock",
    "save",
    [params]
  );
};

Smartlock.prototype.delete = function (params, successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "Smartlock",
    "delete",
    [params]
  );
};

module.exports = new Smartlock();
