package com.okode.cordova.smartlock;

import com.google.android.gms.auth.api.credentials.Credential;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartlockPlugin extends CordovaPlugin {

    private static final String TAG = "SmartlockPlugin";
    Smartlock smartlock;

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {
        this.smartlock = new Smartlock();
        this.smartlock.initialize(callbackContext);

        if (action.equals("request")) {
            this.smartlock.executeRequest();
            return true;
        }
        if (action.equals("save")){
            Credential credential = parseSaveRequest(args);
            this.smartlock.executeSave(credential);
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
            this.smartlock.sendError(PluginError.SMARTLOCK__SAVE__BAD_REQUEST);
        }
    }

}
