package com.yudiol;

import com.yudiol.config.ApplicationContext;
import com.yudiol.controller.Controller;
import com.yudiol.controller.HelperController;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private final Map<String, Controller> controllers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        ApplicationContext context = null;
        Controller controller = null;
        try {
            context = new ApplicationContext();
            context.getInstance(HelperService.class);
            context.getInstance(HelperRepository.class);
            controller = context.getInstance(HelperController.class);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        controllers.put("GET/help-service/v1/support", controller);
        controllers.put("POST/help-service/v1/support", controller);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String method = request.getMethod();
        String key = method + path;
        Controller controller = controllers.get(key);
        if (controller != null) {
            switch (method) {
                case ("GET") -> {
                    controller.getRequest(request, response);
                }
                case ("POST") -> {
                    controller.postRequest(request, response);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
