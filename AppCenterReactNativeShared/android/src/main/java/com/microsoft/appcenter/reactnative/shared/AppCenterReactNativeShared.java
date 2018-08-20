package com.microsoft.appcenter.reactnative.shared;

import android.app.Application;

import java.util.Map;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.InputStream;

import com.microsoft.appcenter.CustomProperties;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.ingestion.models.WrapperSdk;


public class AppCenterReactNativeShared {
    private static String appSecret;
    private static Application application;
    private static WrapperSdk wrapperSdk = new WrapperSdk();

    public static void configureAppCenter(Application application) {
        if (AppCenter.isConfigured()) {
            return;
        }
        AppCenterReactNativeShared.application = application;

        AppCenterReactNativeShared.wrapperSdk.setWrapperSdkVersion(com.microsoft.appcenter.reactnative.shared.BuildConfig.VERSION_NAME);
        AppCenterReactNativeShared.wrapperSdk.setWrapperSdkName(com.microsoft.appcenter.reactnative.shared.BuildConfig.SDK_NAME);

        AppCenter.setWrapperSdk(wrapperSdk);
        AppCenter.configure(application, AppCenterReactNativeShared.getAppSecret());
    }

    /**
        This functionality is intended to allow individual react-native App Center beacons to
        set specific components of the wrapperSDK cooperatively
        E.g. code push can fetch the wrapperSdk, set the code push version, then set the
        wrapperSdk again so it can take effect.
    */
    public static void setWrapperSdk(WrapperSdk wrapperSdk) {
        AppCenterReactNativeShared.wrapperSdk = wrapperSdk;
        AppCenter.setWrapperSdk(wrapperSdk);
    }

    public static WrapperSdk getWrapperSdk() {
        return AppCenterReactNativeShared.wrapperSdk;
    }

    public static void setAppSecret(String secret) {
        AppCenterReactNativeShared.appSecret = secret;
    }

    public static String getAppSecret() {
        if (AppCenterReactNativeShared.appSecret == null) {
            try {
                InputStream configStream = AppCenterReactNativeShared.application.getAssets().open("appcenter-config.json");
                int size = configStream.available();
                byte[] buffer = new byte[size];
                configStream.read(buffer);
                configStream.close();
                String jsonContents = new String(buffer, "UTF-8");
                JSONObject json = new JSONObject(jsonContents);
                AppCenterReactNativeShared.appSecret = json.getString("app_secret");
            } catch (Exception e) {
                // Unable to read secret from file
                // Leave the secret null so that App Center errors out appropriately.
            }
        }

        return AppCenterReactNativeShared.appSecret;
    }
}
