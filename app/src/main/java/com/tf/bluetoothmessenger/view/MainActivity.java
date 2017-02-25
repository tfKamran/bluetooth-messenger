package com.tf.bluetoothmessenger.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tf.bluetoothmessenger.R;
import com.tf.bluetoothmessenger.data.BluetoothManager;
import com.tf.bluetoothmessenger.model.BTDevice;

import java.util.ArrayList;
import java.util.List;

import io.palaima.smoothbluetooth.Device;

public class MainActivity extends AppCompatActivity implements BluetoothManager.OnDeviceFoundListener {

    private ListView mListNearbyDevices;
    private BluetoothDeviceAdapter mAdapter;

    private BluetoothManager mBluetoothManager;
    private boolean wasBluetoothDisabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListNearbyDevices = (ListView) findViewById(R.id.list_nearby_devices);
        mAdapter = new BluetoothDeviceAdapter(MainActivity.this);
        mListNearbyDevices.setAdapter(mAdapter);

        mBluetoothManager = BluetoothManager.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothManager.isBluetoothEnabled()) {
            mBluetoothManager.enableBluetooth();

            wasBluetoothDisabled = true;
        }

        mBluetoothManager.addOnDeviceFoundListener(this);
        mBluetoothManager.scanForNearByDevices();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mBluetoothManager.stopScanForNearByDevices();
        mBluetoothManager.removeOnDeviceFoundListener(this);

        if (wasBluetoothDisabled) {
            mBluetoothManager.disableBluetooth();
        }
    }

    @Override
    public void onFound(Device device) {
        mAdapter.add(new BTDevice(device));
    }

    private class BluetoothDeviceAdapter extends ArrayAdapter<BTDevice> {

        List<BTDevice> items = new ArrayList<>();

        public BluetoothDeviceAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public void add(BTDevice device) {
            if (!items.contains(device)) {
                items.add(device);

                notifyDataSetChanged();
            }
        }

        @Override
        public void remove(BTDevice device) {
            items.remove(device);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public BTDevice getItem(int position) {
            return items.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            ((TextView) convertView).setText(getItem(position).getName());

            return convertView;
        }
    }
}
