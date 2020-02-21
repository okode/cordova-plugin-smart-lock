/*global cordova */

var Smartlock = function() {
};

// Plugin Errors
Smartlock.prototype.BIOMETRIC_UNKNOWN_ERROR = -100;
Smartlock.prototype.BIOMETRIC_UNAVAILABLE = -101;
Smartlock.prototype.BIOMETRIC_AUTHENTICATION_FAILED = -102;
Smartlock.prototype.BIOMETRIC_SDK_NOT_SUPPORTED = -103;
Smartlock.prototype.BIOMETRIC_HARDWARE_NOT_SUPPORTED = -104;
Smartlock.prototype.BIOMETRIC_PERMISSION_NOT_GRANTED = -105;
Smartlock.prototype.BIOMETRIC_NOT_ENROLLED = -106;
Smartlock.prototype.BIOMETRIC_INTERNAL_PLUGIN_ERROR = -107;
Smartlock.prototype.BIOMETRIC_DISMISSED = -108;
Smartlock.prototype.BIOMETRIC_PIN_OR_PATTERN_DISMISSED = -109;
Smartlock.prototype.BIOMETRIC_SCREEN_GUARD_UNSECURED = -110;
Smartlock.prototype.BIOMETRIC_LOCKED_OUT = -111;
Smartlock.prototype.BIOMETRIC_LOCKED_OUT_PERMANENT = -112;

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

Smartlock.prototype.delete = function (successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "Smartlock",
    "delete",
    [params]
  );
};

module.exports = new Smartlock();
