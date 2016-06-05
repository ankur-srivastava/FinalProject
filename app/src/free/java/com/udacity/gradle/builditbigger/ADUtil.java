package com.udacity.gradle.builditbigger;

import com.google.android.gms.ads.AdRequest;

/**
 * Created by ankursrivastava on 6/5/16.
 */
public class ADUtil {
    public static AdRequest getAdRequest(){
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }
}
