package org.app;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SimpleHTTPServer server = new SimpleHTTPServer(8080);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}