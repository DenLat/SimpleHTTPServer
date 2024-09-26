package org.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHTTPServer {
    private int port;

    public SimpleHTTPServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                handleRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Error handling request: " + e.getMessage());
            }
        }
    }

    private void handleRequest(Socket clientSocket) throws IOException {
        HttpRequest request = new HttpRequest(clientSocket);
        HttpResponse response;

        if ("/".equals(request.getPath())) {
            String content = loadResource("/index.html");
            if (content != null) {
                response = new HttpResponse(200, "text/html", content);
            } else {
                response = new HttpResponse(404, "text/plain", "Home page not found");
            }
        } else if (request.getPath().startsWith("/static/")) {
            String resourcePath = request.getPath().substring(7);
            String content = loadResource("/" + resourcePath);
            if (content != null) {
                String contentType = getContentType(resourcePath);
                response = new HttpResponse(200, contentType, content);
            } else {
                response = new HttpResponse(404, "text/plain", "File not found");
            }
        } else {
            response = new HttpResponse(404, "text/plain", "Page not found");
        }

        response.send(clientSocket.getOutputStream());
    }

    private String loadResource(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                return null;
            }
            return new String(is.readAllBytes());
        } catch (IOException e) {
            System.err.println("Error loading resource " + path + ": " + e.getMessage());
            return null;
        }
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        return "text/plain";
    }
}
