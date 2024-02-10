package com.yudiol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yudiol.configuration.ApplicationContext;
import com.yudiol.configuration.HandlerMapping;
import com.yudiol.controller.HelperController;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends HttpServlet {

    private ApplicationContext context = null;
    private HandlerMapping mapping = null;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        try {
            context = new ApplicationContext();

            context.getInstance(HelperService.class);
            context.getInstance(HelperRepository.class);
            context.getInstance(HelperController.class);
            mapping = context.getInstance(HandlerMapping.class);

        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        Method method = mapping.getMethod(request);

        if (method != null) {
            try {
                Object result = method.invoke(context.getInstance(method.getDeclaringClass()), mapping.getArgs(request));
                if (result != null) {
                    response.getWriter().write(objectMapper.writeValueAsString(result));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(400);
        }
    }
}
