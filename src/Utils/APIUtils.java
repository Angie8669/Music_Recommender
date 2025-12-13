package Utils;

public class APIUtils {
    public static String encodeParams(String urlParams) {
        // Replace spaces with "+"
        urlParams = urlParams.replace(" ", "+");
        // Replace quotes with "%22"
        urlParams = urlParams.replace("\"", "%22");

        return urlParams;
    }
}
