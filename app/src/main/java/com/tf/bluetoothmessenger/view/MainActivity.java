package com.tf.bluetoothmessenger.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tf.bluetoothmessenger.R;
import com.tf.bluetoothmessenger.data.BluetoothManager;

import java.util.ArrayList;

import io.palaima.smoothbluetooth.Device;

public class MainActivity extends AppCompatActivity implements BluetoothManager.OnDeviceFoundListener {

    private BluetoothManager mBluetoothManager;
    private ListView mListNearbyDevices;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListNearbyDevices = (ListView) findViewById(R.id.list_nearby_devices);
        mAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, new ArrayList<String>(0));
        mListNearbyDevices.setAdapter(mAdapter);

        mBluetoothManager = BluetoothManager.getInstance(this);

        if (!mBluetoothManager.isBluetoothEnabled()) {
            mBluetoothManager.enableBluetooth();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mBluetoothManager.addOnDeviceFoundListener(this);
        mBluetoothManager.scanForNearByDevices();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mBluetoothManager.stopScanForNearByDevices();
        mBluetoothManager.removeOnDeviceFoundListener(this);
    }

    @Override
    public void onFound(Device device) {
        mAdapter.add(device.getName());
    }
}
