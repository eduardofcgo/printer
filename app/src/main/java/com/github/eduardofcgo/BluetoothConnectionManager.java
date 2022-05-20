package com.github.eduardofcgo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections;

public class BluetoothConnectionManager {

    private final BluetoothConnections connections;

    public BluetoothConnectionManager() {
        this.connections = new BluetoothConnections();
    }

    @SuppressLint("MissingPermission")
    public BluetoothConnection getDefault() {
        for (BluetoothConnection connection : list()) {
            BluetoothDevice device = connection.getDevice();
            String name = device.getName();

            if (name.equals("InnerPrinter")) {
                return connection;
            }
        }

        throw new UnsupportedOperationException("Device does not have InnerPrinter");
    }

    public BluetoothConnection[] list() {
        return connections.getList();
    }
}
