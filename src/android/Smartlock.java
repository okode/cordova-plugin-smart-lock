package com.okode.cordova.smartlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.exxbrain.android.biometric.BiometricManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Smartlock extends CordovaPlugin {

    private static final String TAG = "Smartlock";
    private CallbackContext mCallbackContext = null;

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {

        this.mCallbackContext = callbackContext;
        Log.v(TAG, "Smartlock action: " + action);

        if (action.equals("request")) {
            executeRequest();
            return true;

        } else if (action.equals("save")){
            executeSave(args);
            return true;
        } else if (action.equals("delete")){
            // TODO
            // executeDelete();
            return true;
        }

        return false;
    }

    private void executeRequest(JSONArray args) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        this.mCallbackContext.sendPluginResult(pluginResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode != REQUEST_CODE_BIOMETRIC) {
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            sendSuccess("biometric_success");
        } else if (intent != null) {
            Bundle extras = intent.getExtras();
            sendError(extras.getInt("code"), extras.getString("message"));
        } else {
            sendError(PluginError.BIOMETRIC_DISMISSED);
        }
    }

    private void sendError(int code, String message) {
        JSONObject resultJson = new JSONObject();
        try {
            resultJson.put("code", code);
            resultJson.put("message", message);

            PluginResult result = new PluginResult(PluginResult.Status.ERROR, resultJson);
            cordova.getActivity().runOnUiThread(() ->
                    this.mCallbackContext.sendPluginResult(result));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void sendSuccess(String message) {
        Log.e(TAG, message);
        cordova.getActivity().runOnUiThread(() ->
                this.mCallbackContext.success(message));
    }
}
