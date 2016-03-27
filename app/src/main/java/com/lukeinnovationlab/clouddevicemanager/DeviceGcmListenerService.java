package com.lukeinnovationlab.clouddevicemanager;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Service handles GCM messages.
 */
public class DeviceGcmListenerService extends GcmListenerService {
    private static final String TAG = "DeviceGcmLS";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        if (getString(R.string.gcm_defaultSenderId).equals(from)) {
            Log.d(TAG, "Message \"" + message + "\" from CloudDeviceManager (" + from + ")");
        } else {
            Log.d(TAG, "Message \"" + message + "\" from unknown sender (" + from + ")");
            return;
        }

        if (from.startsWith("/topics/")) {
            // message received from some topic.
            Log.d(TAG, "Message received from topic: " + from);
        } else {
            // normal downstream message.
            Log.d(TAG, "Normal downstream message");
            replyMessage();
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        // [END_EXCLUDE]
    }

    private void replyMessage() {
        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this
                .getApplicationContext());
        final String senderId = getString(R.string.gcm_defaultSenderId);
        final String msgId = String.valueOf(System.currentTimeMillis());

        String userName = getUserName();
        String deviceId = getDeviceId();
        Bundle uploadData = new Bundle();
        uploadData.putString("username", userName);
        uploadData.putString("deviceid", deviceId);

        try {
            gcm.send(senderId + "@gcm.googleapis.com", msgId, uploadData);
        } catch (IOException e) {
            Log.e(TAG, "Failed to send via GCM for msg: " + msgId);
        }
    }

    @Override
    public void onMessageSent(String msgId) {
        Log.d(TAG, "onMessageSent: " + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        Log.d(TAG, "onSendError: " + msgId + ", " + error);
    }

    public String getUserName() {
        return "tester1";
    }

    public String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context
                .TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    // [END receive_message]
}
