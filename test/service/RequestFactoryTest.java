package service;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

class RequestFactoryTest {

    @Test
    void createGetConnection() {
        HttpURLConnection connection = RequestFactory.createConnection("GET", "https://localhost/", "path", "?param=12", "12345");

        assertEquals("https://localhost/path?param=12", connection.getURL().toString());
        assertEquals("GET", connection.getRequestMethod());
    }

    @Test
    void createPostConnection() {
        HttpURLConnection connection = RequestFactory.createConnection("POST", "https://localhost/", "path", "?param=12", "12345");

        assertEquals("https://localhost/path", connection.getURL().toString());
        assertEquals("POST", connection.getRequestMethod());
        assertEquals("application/x-www-form-urlencoded", connection.getRequestProperty("Content-Type"));
    }
}