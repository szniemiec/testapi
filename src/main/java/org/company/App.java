package org.company;

import org.company.server.ServerApp;

public class App {

    public static void main(String[] args) throws Exception {
        ServerApp server = new ServerApp();
        server.run();
    }
}