package com.yudiol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yudiol.configuration.ApplicationContext;
import com.yudiol.configuration.HandlerMapping;

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
            mapping = new HandlerMapping();
            context = new ApplicationContext();
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
                Object result = method.invoke(context.getInstance(method.getDeclaringClass()), mapping.getArgs(request,response));
                if (result != null) {
                    response.getWriter().write(objectMapper.writeValueAsString(result));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(400);
        }
    }
}
