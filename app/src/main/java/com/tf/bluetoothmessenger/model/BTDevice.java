package com.tf.bluetoothmessenger.model;

import android.bluetooth.BluetoothDevice;

import io.palaima.smoothbluetooth.Device;

/**
 * Created by kamran on 25/02/17.
 */

public class BTDevice {

    private String address;
    private String name;

    public BTDevice(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public BTDevice(BluetoothDevice bluetoothDevice) {
        this(bluetoothDevice.getAddress(), bluetoothDevice.getName());
    }

    public BTDevice(Device bluetoothDevice) {
        this(bluetoothDevice.getAddress(), bluetoothDevice.getName());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return address.equals(((BTDevice) obj).getAddress());
    }
}
