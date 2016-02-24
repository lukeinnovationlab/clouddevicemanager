package com.lukeinnovationlab.clouddevicemanager;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Service handles the creation, rotation, and updating of registration tokens.
 */
public class DeviceInstanceIDListenerService extends InstanceIDListenerService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if
        // applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
    // [END refresh_token]
}
