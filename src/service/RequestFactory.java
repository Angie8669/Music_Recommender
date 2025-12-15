package service;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class RequestFactory {
    public static HttpURLConnection createConnection(String method, String baseUrl, String path, String params, String accessToken) {
        // Inspired by https://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java
        HttpURLConnection connection = null;
        StringBuilder urlString = new StringBuilder();
        urlString.append(baseUrl);
        urlString.append(path);
        if (method.equals("GET")) {
            // Add params to URL for GET requests.
            urlString.append(params);
        }

        try {
            URL url = URI.create(urlString.toString()).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (method.equals("POST")) {
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
            } else if (method.equals("GET")) {
                connection.setRequestProperty("Authorization",
                        "Bearer " + accessToken);
            }

            connection.setUseCaches(false);
            connection.setDoOutput(true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return connection;
    }
}
