package org.apache.cordova.smartlock;

import com.google.android.gms.auth.api.credentials.Credential;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Smartlock extends CordovaPlugin {

    private static final String TAG = "Smartlock";
    private static SmartlockManager smartlockManager;

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {
        if (smartlockManager == null) {
            smartlockManager = new SmartlockManager(callbackContext, cordova.getActivity());
        }

        if (action.equals("request")) {
            smartlockManager.executeRequest();
            return true;
        }
        if (action.equals("save")){
            Credential credential = parseSaveRequest(args);
            smartlockManager.executeSave(credential);
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
            smartlockManager.sendError(PluginError.SMARTLOCK__SAVE__BAD_REQUEST);
        }
        return null;
    }

}
