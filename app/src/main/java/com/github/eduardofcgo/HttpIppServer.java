package com.github.eduardofcgo;

import com.hp.jipp.trans.IppServerTransport;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpIppServer {

    private HttpServer server;

    private final String path;
    private final int port;
    private final IppServerTransport transport;

    public HttpIppServer(String path, int port, IppServerTransport transport) {
        this.transport = transport;
        this.path = path;
        this.port = port;
        this.server = null;
    }

    public void start() throws IOException {
        if (server == null) {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext(path, new HttpIppHandler(transport));
            server.setExecutor(null);
            server.start();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    public boolean isRunning() {
        return server != null;
    }
}