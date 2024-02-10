package com.yudiol.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yudiol.annotation.GetMapping;
import com.yudiol.annotation.PostMapping;
import com.yudiol.annotation.RestController;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMapping {

    private final Map<String, Method> endpoints = new ConcurrentHashMap<>();
    private final Map<String, Object[]> parameters = new ConcurrentHashMap<>();

    public HandlerMapping() {
        Reflections reflections = new Reflections("com.yudiol.controller");
        List<Method> methods = reflections.getTypesAnnotatedWith(RestController.class)
                .stream().flatMap(type -> Arrays.stream(type.getDeclaredMethods())).toList();

        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                String paths = "GET" + method.getAnnotation(GetMapping.class).path();
                endpoints.put(paths, method);
            }
            if (method.isAnnotationPresent(PostMapping.class)) {
                String paths = "POST" + method.getAnnotation(PostMapping.class).path();
                endpoints.put(paths, method);
                parameters.put(paths, Arrays.stream(method.getParameters()).map(Parameter::getType).toArray());
            }
        }
    }


    public Method getMethod(HttpServletRequest request) {
        String key = request.getMethod() + request.getRequestURI();
        return endpoints.get(key);
    }

    public Object[] getArgs(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();
        StringBuilder jsonInput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }

        String path = request.getRequestURI();
        String meth = request.getMethod();
        String key = meth + path;

        Object[] args = null;
        ObjectMapper objectMapper = new ObjectMapper();

        if (meth.equals("POST")) {
            Class<?> parameterType = (Class<?>) parameters.get(key)[0];
            args = new Object[]{objectMapper.readValue(jsonInput.toString(), parameterType)};
        }
        return args;
    }
}
