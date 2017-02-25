package com.tf.bluetoothmessenger.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tf.bluetoothmessenger.R;
import com.tf.bluetoothmessenger.data.BluetoothManager;
import com.tf.bluetoothmessenger.model.BTDevice;

public class MainActivity extends AppCompatActivity {

    private BluetoothManager mBluetoothManager;
    private boolean wasBluetoothDisabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothManager = BluetoothManager.getInstance(MainActivity.this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, DevicesFragment.getInstance())
                .commit();
    }

    public void initiateChat(BTDevice device) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, ConversationFragment.getInstance(device))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothManager.isBluetoothEnabled()) {
            mBluetoothManager.enableBluetooth();

            wasBluetoothDisabled = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (wasBluetoothDisabled) {
            mBluetoothManager.disableBluetooth();
        }
    }
}
