package org.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHTTPServer {
    private int port;

    public SimpleHTTPServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту " + port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                handleRequest(clientSocket);
            }
        }
    }

    private void handleRequest(Socket clientSocket) throws IOException {
        // Логика обработки запроса будет добавлена позже
    }
}
