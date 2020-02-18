package com.okode.cordova.smartlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.exxbrain.android.biometric.BiometricPrompt;

import java.util.concurrent.Executor;

public enum PluginError {

    SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND(10),
    SMARTLOCK__REQUEST__DIALOG_CANCELLED(12),
    
    SMARTLOCK__SAVE(13),
    
    SMARTLOCK__DELETE(14),
    
    SMARTLOCK__COMMON__UNKOWN(11),
    SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL(15);

    private int value;
    private String message;

    PluginError(int value) {
        this.value = value;
        this.message = this.name();
    }

    PluginError(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}