package com.tf.bluetoothmessenger.data;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

/**
 * Created by kamran on 25/02/17.
 */

public class BluetoothManager implements SmoothBluetooth.Listener {

    private static final String TAG = BluetoothManager.class.getSimpleName();

    private static BluetoothManager mInstance;
    private final Context mContext;
    private final SmoothBluetooth mSmoothBluetooth;
    private List<OnDeviceFoundListener> mOnDeviceFoundListeners = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter;

    public static BluetoothManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BluetoothManager(context);
        }

        return mInstance;
    }

    private BluetoothManager(Context context) {
        mContext = context;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mSmoothBluetooth = new SmoothBluetooth(mContext, this);
    }

    public void enableBluetooth() {
        if (!isBluetoothEnabled()) {
            mBluetoothAdapter.enable();
        }
    }

    public boolean isBluetoothEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    public void scanForNearByDevices() {
        mSmoothBluetooth.startDiscovery();
        mSmoothBluetooth.tryConnection();
        mSmoothBluetooth.doDiscovery();
    }

    public void stopScanForNearByDevices() {
        mSmoothBluetooth.cancelDiscovery();
    }

    @Override
    public void onBluetoothNotSupported() {

    }

    @Override
    public void onBluetoothNotEnabled() {

    }

    @Override
    public void onConnecting(Device device) {

    }

    @Override
    public void onConnected(Device device) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(Device device) {

    }

    @Override
    public void onDiscoveryStarted() {
        Log.d(TAG, "onDiscoveryStarted");
    }

    @Override
    public void onDiscoveryFinished() {
        Log.d(TAG, "onDiscoveryFinished");
    }

    @Override
    public void onNoDevicesFound() {
        Log.d(TAG, "onNoDevicesFound");
    }

    @Override
    public void onDevicesFound(List<Device> deviceList, SmoothBluetooth.ConnectionCallback connectionCallback) {
        for (OnDeviceFoundListener listener : mOnDeviceFoundListeners) {
            for (Device device : deviceList) {
                listener.onFound(device);
            }
        }
    }

    @Override
    public void onDataReceived(int data) {

    }

    public void addOnDeviceFoundListener(OnDeviceFoundListener listener) {
        mOnDeviceFoundListeners.add(listener);
    }

    public void removeOnDeviceFoundListener(OnDeviceFoundListener listener) {
        mOnDeviceFoundListeners.remove(listener);
    }

    public interface OnDeviceFoundListener {
        void onFound(Device device);
    }
}
