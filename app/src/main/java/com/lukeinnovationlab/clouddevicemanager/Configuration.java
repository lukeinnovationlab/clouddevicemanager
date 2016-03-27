package com.lukeinnovationlab.clouddevicemanager;

/**
 * GCM client app configuration class.
 */
public final class Configuration {
    private static final boolean LOCAL_MODE = false;

    public static boolean isLocalMode() {
        return LOCAL_MODE;
    }
}
