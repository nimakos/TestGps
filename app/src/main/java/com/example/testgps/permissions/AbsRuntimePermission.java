package com.example.testgps.permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.SparseIntArray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testgps.R;
import com.google.android.material.snackbar.Snackbar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Asks for runTime permissions
 */
public abstract class AbsRuntimePermission extends AppCompatActivity {
    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
    }

    /**
     * If all goes well (if user has accepted all permissions)
     * This function automatically is being triggered
     *
     * @param requestCode A request code for permission
     */
    public abstract void onPermissionsGranted(int requestCode);

    /**
     * Request permissions method
     *
     * @param requestedPermissions The requested permissions on a string array
     * @param requestCode          The request code permission
     */
    public void requestAppPermissions(final String[] requestedPermissions, final int requestCode) {
        mErrorString.put(requestCode, R.string.permission);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    /**
     * Check permissions from user
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }

        if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionsGranted(requestCode);
        } else {

            //Display message when contain some Dangerous permission not accept
            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode),
                    Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.settings), v -> {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.setData(Uri.parse("package:" + getPackageName()));
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
            }).show();
        }
    }
}