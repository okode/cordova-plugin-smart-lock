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

public class SmartlockActivity extends AppCompatActivity {

    private static final String TAG = "SmartlockActivity";
    private GoogleApiClient googleApiClient;
    private static boolean mIsResolving = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CredentialsOptions credentialsOptions = new CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build();

        googleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, 0, this)
            .addApi(Auth.CREDENTIALS_API, credentialsOptions)
            .build();
    }

    public void request() {
        CredentialRequest credentialRequest = new CredentialRequest.Builder()
            .setPasswordLoginSupported(true)
            .build();

        Auth.CredentialsApi.request(googleApiClient, credentialRequest)
            .setResultCallback(new ResultCallback<CredentialRequestResult>() {
                @Override
                public void onResult(@NonNull CredentialRequestResult credentialRequestResult) {
                    Status status = credentialRequestResult.getStatus();
                    Log.d(TAG, "Get status " + credentialRequestResult);
                    if (status.isSuccess()) {
                        // SUCCESS REQUEST TODO
                        credentialRequestResult.getCredential();
                    } else if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
                        resolveResult(status, RC_READ);
                    } else if (status.getStatusCode() == CommonStatusCodes.SIGN_IN_REQUIRED) {
                        Log.d(TAG, "Sign in required");
                        // ERROR REQUEST (Empty, not found cases)
                        finishWithError(PluginError.SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND);
                    } else {
                        Log.w(TAG, "Unrecognized status code: " + status.getStatusCode());
                        // ERROR REQUEST (Unknown)
                        finishWithError(
                            PluginError.SMARTLOCK__COMMON__UNKOWN.getValue(),
                            status.geStatusCode() + " - " + status.getStatusMessage());
                    }
                }
            });
    }

    private void save(Credential credential) {
        Auth.CredentialsApi.save(googleApiClient, credential)
            .setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Log.d(TAG, "Credential saved");
                        // SUCCESS SAVE TODO

                    } else {
                        resolveResult(status, RC_SAVE);
                    }
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Result code: " + resultCode);

        switch (requestCode) {
            case RC_READ:
                if (resultCode == RESULT_OK) {
                    // SUCCESS REQUEST TODO
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                } else {
                    Log.d(TAG, "Request failed");
                    // ERROR REQUEST
                    finishWithError(PluginError.SMARTLOCK__REQUEST__DIALOG_CANCELLED);
                }
                break;
            case RC_SAVE:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Credential Save: OK");
                    // SUCCESS SAVE TODO
                } else {
                    Log.e(TAG, "Credential Save Failed");
                    // ERROR SAVE
                    finishWithError(PluginError.SMARTLOCK__SAVE);
                }
                break;
        }

        mIsResolving = false;
    }

    private void resolveResult(Status status, int requestCode) {
        if (mIsResolving) {
            Log.w(TAG, "resolveResult: already resolving.");
            return;
        }

        Log.d(TAG, "Resolving: " + status);
        if (status.hasResolution()) {
            try {
                status.startResolutionForResult(this, requestCode);
                mIsResolving = true;
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "STATUS: Failed to send resolution.", e);
                // ERROR (WEIRD) Resolution REQUEST/SAVE
                finishWithError(PluginError.SMARTLOCK__RESOLUTION_PROMPT_FAIL);
            }
        } else {
            finishWithError(
                PluginError.SMARTLOCK__COMMON__UNKOWN.getValue(),
                status.geStatusCode() + " - " + status.getStatusMessage());
        }
    }

    private void finishWithSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    private void finishWithError(PluginError error) {
        finishWithError(error.getValue(), error.getMessage());
    }

    private void finishWithError(int code, String message) {
        Intent data = new Intent();
        data.putExtra("code", code);
        data.putExtra("message", message);
        setResult(RESULT_CANCELED, data);
        finish();
    }
}
