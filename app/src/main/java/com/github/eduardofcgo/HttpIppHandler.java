package com.github.eduardofcgo;

import com.google.common.io.ByteStreams;
import com.hp.jipp.encoding.IppInputStream;
import com.hp.jipp.encoding.IppOutputStream;
import com.hp.jipp.trans.IppPacketData;
import com.hp.jipp.trans.IppServerTransport;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpIppHandler implements HttpHandler {

    private final IppServerTransport transport;

    public HttpIppHandler(IppServerTransport server) {
        this.transport = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        IppInputStream inputStream = new IppInputStream(exchange.getRequestBody());
        IppPacketData ippRequest = new IppPacketData(inputStream.readPacket(), inputStream);

        IppPacketData response = transport.handle(exchange.getRequestURI(), ippRequest);

        exchange.sendResponseHeaders(200, 0);

        try (OutputStream output = exchange.getResponseBody()) {
            new IppOutputStream(output).write(response.getPacket());

            InputStream extraData = response.getData();
            if (extraData != null) {
                ByteStreams.copy(extraData, output);
                extraData.close();
            }
        }
    }
}
