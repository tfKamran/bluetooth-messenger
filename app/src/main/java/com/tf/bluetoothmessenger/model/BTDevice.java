package com.tf.bluetoothmessenger.model;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import io.palaima.smoothbluetooth.Device;

/**
 * Created by kamran on 25/02/17.
 */

public class BTDevice implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.name);
    }

    protected BTDevice(Parcel in) {
        this.address = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<BTDevice> CREATOR = new Parcelable.Creator<BTDevice>() {
        @Override
        public BTDevice createFromParcel(Parcel source) {
            return new BTDevice(source);
        }

        @Override
        public BTDevice[] newArray(int size) {
            return new BTDevice[size];
        }
    };
}
