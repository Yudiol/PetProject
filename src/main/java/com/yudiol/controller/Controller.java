package com.yudiol.controller;

import com.yudiol.service.HelperService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void getRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void postRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void setHelperService(HelperService helperService);
}