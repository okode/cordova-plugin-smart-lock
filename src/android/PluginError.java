package org.apache.cordova.smartlock;

public enum PluginError {

    SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND(10),
    SMARTLOCK__REQUEST__DIALOG_CANCELLED(12),

    SMARTLOCK__SAVE(13),
    SMARTLOCK__SAVE__BAD_REQUEST(13),

    SMARTLOCK__DELETE(14),
    SMARTLOCK__COMMON__UNKOWN(11),
    SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL(15);

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
