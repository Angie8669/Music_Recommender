package Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class APIUtilsTest {

    @Test
    void encodeParams() {
        String params = APIUtils.encodeParams("q=\"Test string\"");

        assertEquals("q=%22Test+string%22", params);
    }
}