package org.company.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Handler implements HttpHandler {

    @Override
    public void handle(final HttpExchange e) throws IOException {
        String response = "";
        e.sendResponseHeaders(200, response.length());
        OutputStream os = e.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}