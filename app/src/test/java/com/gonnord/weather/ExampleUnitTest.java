package com.gonnord.weather;

import com.gonnord.weather.model.data.Response;
import com.gonnord.weather.utils.StringUtils;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void jsonResponseParsing() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("response.json");
        String jsonResponse = StringUtils.getStringFromInputStream(is);

        Response response = new Gson().fromJson(jsonResponse, Response.class);

        assertNotNull(response);
        assertEquals(1511953200, response.getForecasts().get(4).getDate());
    }
}