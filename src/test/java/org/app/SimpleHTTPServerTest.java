package org.app;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;

class SimpleHTTPServerTest {

    @Test
    void testServerResponse() throws IOException {
        SimpleHTTPServer server = new SimpleHTTPServer(8081);
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Give the server some time to start
        try {
            Thread.sleep(1000); // Wait for the server to start
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Test the server response
        URL url = new URL("http://localhost:8081/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        assertEquals(200, status);

        con.disconnect();
    }
}
