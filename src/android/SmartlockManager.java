package org.apache.cordova.smartlock;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartlockManager  {

    private static final String TAG = "SmartlockManager";

    private GoogleApiClient googleApiClient;
    private Activity cordovaActivity;

    public interface ReadyListener {
        public void ready();
        public void fail();
    }

    public SmartlockManager(Activity cordovaActivity) {
        this.cordovaActivity = cordovaActivity;
    }

    public void connect(ReadyListener listener) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            listener.ready();
            return;
        }
        CredentialsOptions credentialsOptions = new CredentialsOptions.Builder()
                .forceEnableSaveDialog()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this.cordovaActivity)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        listener.fail();
                    }
                })
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        listener.ready();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        listener.fail();
                    }


                })
                .addApi(Auth.CREDENTIALS_API, credentialsOptions)
                .build();
        googleApiClient.connect();
    }

    public void executeRequest(ResultCallback callback) {
        CredentialRequest credentialRequest = new CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .build();

        Auth.CredentialsApi.request(googleApiClient, credentialRequest)
                .setResultCallback(callback);
    }

    public void executeSave(Credential credential, ResultCallback callback) {
        Auth.CredentialsApi.save(googleApiClient, credential)
                .setResultCallback(callback);
    }

}
