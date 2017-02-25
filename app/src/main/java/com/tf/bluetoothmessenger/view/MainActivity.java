package com.tf.bluetoothmessenger.view;

import android.bluetooth.BluetoothDevice;
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
import java.util.Set;

public class MainActivity extends AppCompatActivity implements BluetoothManager.OnBluetoothEnabledListener {

    private View mProgressBar;
    private BluetoothDeviceAdapter mAdapter;

    private BluetoothManager mBluetoothManager;
    private boolean wasBluetoothDisabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        ListView mListNearbyDevices = (ListView) findViewById(R.id.list_nearby_devices);

        mAdapter = new BluetoothDeviceAdapter(MainActivity.this);
        mListNearbyDevices.setAdapter(mAdapter);

        mBluetoothManager = BluetoothManager.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothManager.isBluetoothEnabled()) {
            mBluetoothManager.enableBluetooth();

            showProgressBar();

            wasBluetoothDisabled = true;
        } else {
            populatePairedDevices();
        }

        mBluetoothManager.addOnBluetoothEnabledListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mBluetoothManager.stopScanForNearByDevices();
        mBluetoothManager.removeOnBluetoothEnabledListener(this);

        if (wasBluetoothDisabled) {
            mBluetoothManager.disableBluetooth();
        }
    }

    private void populatePairedDevices() {
        final Set<BluetoothDevice> pairedDevices = mBluetoothManager.getPairedDevices();

        for (BluetoothDevice pairedDevice : pairedDevices) {
            mAdapter.add(new BTDevice(pairedDevice));
        }

        hideProgressBar();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onEnabled() {
        populatePairedDevices();
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
