package org.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHTTPServer {
    private int port;
    private ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(SimpleHTTPServer.class);

    public SimpleHTTPServer(int port) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("Server started on port {}", port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(() -> {
                try {
                    handleRequest(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
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
