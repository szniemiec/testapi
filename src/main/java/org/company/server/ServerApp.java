package org.company.server;

import com.sun.net.httpserver.HttpServer;
import org.company.handlers.Handler;

import java.net.InetSocketAddress;

public class ServerApp {

    public void run() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new Handler());
        server.start();
    }
}
