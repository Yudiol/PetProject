package com.yudiol.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yudiol.annotation.RestController;
import com.yudiol.provider.ProviderHandlerMapping;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
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
            Annotation[] annotations = method.getAnnotations();
            String paths = null;
            if (annotations.length == 0) {
                continue;
            }
            paths = new ProviderHandlerMapping().getProvider(annotations, method);
            endpoints.put(paths, method);
            parameters.put(paths, Arrays.stream(method.getParameters()).map(Parameter::getType).toArray());
        }
    }

    public Method getMethod(HttpServletRequest request) {
        String key = request.getMethod() + request.getRequestURI();
        return endpoints.get(key);
    }

    public Object[] getArgs(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object[] args = null;
        String key = request.getMethod() + request.getRequestURI();

        if (parameters.get(key).length == 0) {
            return args;
        }

        BufferedReader reader = request.getReader();
        StringBuilder phrase = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            phrase.append(line);
        }
        try {
            Class<?> parameterType = (Class<?>) parameters.get(key)[0];
            args = new Object[]{new ObjectMapper().readValue(phrase.toString(), parameterType)};
        } catch (Exception e) {
            response.setStatus(400);
        }
        return args;
    }
}
