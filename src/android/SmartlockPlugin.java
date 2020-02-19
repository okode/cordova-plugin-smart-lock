package com.okode.cordova.smartlock;

import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartlockPlugin extends CordovaPlugin {

    private static final String TAG = "SmartlockPlugin";

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {
        Smartlock smartlock = new Smartlock();
        smartlock.initialize(callbackContext);

        if (action.equals("request")) {
            smartlock.executeRequest();
            return true;
        }
        if (action.equals("save")){
            Credential credential = parseSaveRequest(args);
            smartlock.executeSave(credential);
            return true;
        }
        if (action.equals("delete")){
            // TODO
            // executeDelete();
            return true;
        }

        return false;
    }

    private Credential parseSaveRequest(JSONArray args) {
        JSONObject argsObject;
        try {
            argsObject = args.getJSONObject(0);
            String id = (String) argsObject.get("id");
            String name = (String) argsObject.get("name");
            String password = (String) argsObject.get("password");

            return new Credential.Builder(id)
                    .setName(name)
                    .setPassword(password)
                    .build();
        } catch (JSONException e) {
            sendError(PluginError.SMARTLOCK__SAVE__BAD_REQUEST);
        }
    }

}
