package org.app;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private int statusCode;
    private String contentType;
    private String content;

    public HttpResponse(int statusCode, String contentType, String content) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.content = content != null ? content : "";
    }

    public void send(OutputStream outputStream) throws IOException {
        String statusLine = "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode) + "\r\n";
        String headers = "Content-Type: " + contentType + "\r\n" +
                         "Content-Length: " + content.getBytes().length + "\r\n" +
                         "\r\n";
        outputStream.write(statusLine.getBytes());
        outputStream.write(headers.getBytes());
        outputStream.write(content.getBytes());
        outputStream.flush();
    }

    private String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200: return "OK";
            case 404: return "Not Found";
            default: return "Internal Server Error";
        }
    }
}
