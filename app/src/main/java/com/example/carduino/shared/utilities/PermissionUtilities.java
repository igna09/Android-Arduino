package com.example.carduino.shared.utilities;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.example.carduino.shared.singletons.ContextsSingleton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionUtilities {
    private final static String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };

    public static boolean haveAllPermissions() {
        Boolean allPermissions = Arrays.stream(permissions).allMatch((el) -> {
            int checkVal = ContextsSingleton.getInstance().getApplicationContext().checkCallingOrSelfPermission(el);
            return checkVal == PackageManager.PERMISSION_GRANTED;
        });
        Boolean writePermission = Settings.System.canWrite(ContextsSingleton.getInstance().getApplicationContext());
        return allPermissions && writePermission;
    }

    public static void requestMissingPermissions() {
        List<String> missingPermissions = Arrays.stream(permissions).filter(s -> {
            int checkVal = ContextsSingleton.getInstance().getApplicationContext().checkCallingOrSelfPermission(s);
            return checkVal == PackageManager.PERMISSION_DENIED;
        }).collect(Collectors.toList());
        if(!missingPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(ContextsSingleton.getInstance().getMainActivityContext(), missingPermissions.toArray(String[]::new), 0);
        }
        Boolean writePermission = Settings.System.canWrite(ContextsSingleton.getInstance().getApplicationContext());
        if(!writePermission) {
            requestWriteSettingsPermission();
        }
    }

    public static void requestWriteSettingsPermission() {
        Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ContextsSingleton.getInstance().getMainActivityContext(), settingsIntent, null);
    }
}
