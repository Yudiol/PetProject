package com.yudiol.controller;

import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class HelperController implements Controller {


    private final HelperService helperService;

    @Override
    public void getRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello, this is the GET");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println(helperService.getRandomPhrase());
    }

    @Override
    public void postRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Логика обработки запроса для домашней страницы
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("Hello, this is the POST!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder phrase = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            phrase.append(line);
        }
        helperService.addPhrase(phrase.toString());
    }

    @Override
    public void setHelperService(HelperService helperService) {

    }
}