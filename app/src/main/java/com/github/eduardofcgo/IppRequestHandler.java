package com.github.eduardofcgo;

import static com.hp.jipp.encoding.AttributeGroup.groupOf;

import androidx.annotation.NonNull;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.google.common.io.ByteStreams;
import com.hp.jipp.encoding.IppPacket;
import com.hp.jipp.encoding.Tag;
import com.hp.jipp.model.Status;
import com.hp.jipp.trans.IppPacketData;
import com.hp.jipp.trans.IppServerTransport;

import java.io.IOException;
import java.net.URI;

public class IppRequestHandler implements IppServerTransport {

    private final DeviceConnection connection;

    public IppRequestHandler(DeviceConnection connection) {
        this.connection = connection;
    }

    @NonNull
    @Override
    public IppPacketData handle(@NonNull URI uri, @NonNull IppPacketData request) throws IOException {
        if (request.getData() != null) {
            try {
                this.connection.connect();
            } catch (EscPosConnectionException e) {
                e.printStackTrace();
                throw new IOException("Unable to connect to device");
            }
            this.connection.write(ByteStreams.toByteArray(request.getData()));
            try {
                this.connection.send();
                this.connection.disconnect();

                IppPacket responsePacket = new IppPacket(Status.successfulOk, 0x123,
                        groupOf(Tag.operationAttributes),
                        groupOf(Tag.printerAttributes));
                return new IppPacketData(responsePacket, null);

            } catch (EscPosConnectionException e) {
                e.printStackTrace();

                throw new IOException("Unable to send to device");
            }
        } else throw new IllegalArgumentException("Null request data");
    }
}
