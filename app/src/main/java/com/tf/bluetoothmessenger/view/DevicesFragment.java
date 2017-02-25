package com.tf.bluetoothmessenger.view;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DevicesFragment extends Fragment implements BluetoothManager.OnBluetoothEnabledListener {

    private View mProgressBar;
    private BluetoothDeviceAdapter mAdapter;

    private BluetoothManager mBluetoothManager;

    public static DevicesFragment getInstance() {
        return new DevicesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.progress_bar);
        ListView mListNearbyDevices = (ListView) view.findViewById(R.id.list_nearby_devices);

        mAdapter = new BluetoothDeviceAdapter(getActivity());
        mListNearbyDevices.setAdapter(mAdapter);

        mBluetoothManager = BluetoothManager.getInstance(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mBluetoothManager.isBluetoothEnabled()) {
            mBluetoothManager.enableBluetooth();

            showProgressBar();
        } else {
            populatePairedDevices();
        }

        mBluetoothManager.addOnBluetoothEnabledListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        mBluetoothManager.removeOnBluetoothEnabledListener(this);
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
