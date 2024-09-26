package org.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpRequest {
    private String method;
    private String path;

    public HttpRequest(Socket clientSocket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String requestLine = reader.readLine();
        String[] parts = requestLine.split(" ");
        this.method = parts[0];
        this.path = parts[1];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
