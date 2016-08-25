package com.github.spezifisch.sensorrawlogger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.spezifisch.sensorrawlogger.Input.GpsInput;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    // permissions
    private static final int REQ_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, GpsInput.class));

        // request GPS permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_FINE_LOCATION);
        } else {
            // already granted
            startGps();
        }
    }

    private void startGps() {
        GpsInput.getInstance().init(getApplicationContext());

        TextView v = (TextView) findViewById(R.id.text_service);
        if (GpsInput.getInstance().isSuccess()) {
            v.setText(getString(R.string.service_running));
        } else {
            v.setText(getString(R.string.service_failed));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQ_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGps();
                } else {
                    Toast.makeText(MainActivity.this, "Restart app for new request.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

}
