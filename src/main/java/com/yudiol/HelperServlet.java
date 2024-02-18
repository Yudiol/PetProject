package com.yudiol;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HelperServlet extends HttpServlet {

    AtomicInteger number = new AtomicInteger(-1);
    Map<Integer, String> phrases = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int random = (int) (Math.random() * phrases.size());
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().println("<html><body><h2>" + phrases.get(random) + "</h2></body></html>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder phrase = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            phrase.append(line);
        }
        phrases.put(number.incrementAndGet(), phrase.toString());
    }

    public Map<Integer, String> getPhrases() {
        return phrases;
    }

}
