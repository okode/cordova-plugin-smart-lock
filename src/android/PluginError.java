package org.apache.cordova.smartlock;

public enum PluginError {

    SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND(-100),
    SMARTLOCK__REQUEST__DIALOG_CANCELLED(-101),

    SMARTLOCK__SAVE(-200),
    SMARTLOCK__SAVE__BAD_REQUEST(-201),

    SMARTLOCK__DELETE(-300),

    SMARTLOCK__DISABLE_AUTO_SIGN_IN(-400),

    SMARTLOCK__COMMON__UNKOWN(-500),
    SMARTLOCK__COMMON__CONCURRENT_NOT_ALLOWED(-501),
    SMARTLOCK__COMMON__GOOGLE_API_UNAVAILABLE(-502),
    SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL(-503);

    private int value;
    private String message;

    PluginError(int value) {
        this.value = value;
        this.message = this.name();
    }
    
    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
