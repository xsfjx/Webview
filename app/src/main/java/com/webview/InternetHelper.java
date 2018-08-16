package com.webview;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetHelper {

    public static boolean isOnline(Context context) {
        boolean isOnline = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (manager != null) {
                isOnline = manager.getActiveNetworkInfo() != null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOnline;
    }
}

