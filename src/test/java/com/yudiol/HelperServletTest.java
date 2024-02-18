package com.yudiol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HelperServletTest {

    private HelperServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws IOException {
        servlet = new HelperServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        StringWriter responseWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(responseWriter);

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void doGet() throws IOException {

        servlet.doGet(request, response);

        verify(response, times(1)).setContentType("text/plain;charset=UTF-8");
        verify(response, times(1)).getWriter();
    }

    @Test
    void doPost() throws IOException {

        String phrase = "У тебя всё получиться";
        when(request.getInputStream()).thenReturn(new MockServletInputStream(phrase.getBytes(StandardCharsets.UTF_8)));

        servlet.doPost(request, response);

        Map<Integer, String> phrases = servlet.getPhrases();
        assertEquals(1, phrases.size());
        assertTrue(phrases.containsValue(phrase));
        verify(request, times(1)).getInputStream();
    }
}

class MockServletInputStream extends ServletInputStream {

    private final InputStream inputStream;

    public MockServletInputStream(byte[] content) {
        this.inputStream = new ByteArrayInputStream(content);
    }

    @Override
    public boolean isFinished() {
        try {
            return inputStream.available() == 0;
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}
