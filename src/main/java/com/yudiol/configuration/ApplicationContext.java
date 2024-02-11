package com.yudiol.configuration;

import com.yudiol.annotation.Configuration;
import com.yudiol.annotation.Instance;
import com.yudiol.annotation.Logged;
import com.yudiol.service.HelperService;
import com.yudiol.service.impl.LoggingManager;
import lombok.Setter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public ApplicationContext() throws InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections("com.yudiol.configuration");
        List<?> configurations = reflections.getTypesAnnotatedWith(Configuration.class)
                .stream().map(type -> {
                    try {
                        return type.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList();

        for (Object configuration : configurations) {
            List<Method> methods = Arrays.stream(configuration.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Instance.class)).toList();
            List<Method> methodsWithoutParams = methods.stream().filter(method -> method.getParameters().length == 0).toList();
            List<Method> methodsWithParams = methods.stream().filter(method -> method.getParameters().length > 0).toList();

            for (Method methodsWithoutParam : methodsWithoutParams) {
                Object instance = addProxy(methodsWithoutParam, configuration, null);
                instances.put(methodsWithoutParam.getReturnType(), instance);
            }

            for (Method methodsWithParam : methodsWithParams) {
                Object[] objects = Arrays.stream(methodsWithParam.getParameters())
                        .map(param -> instances.get(param.getType()))
                        .toArray();
                Object instance = addProxy(methodsWithParam, configuration, objects);
                instances.put(methodsWithParam.getReturnType(), instance);
            }
        }
    }

    private Object addProxy(Method method, Object configuration, Object[] objects) throws InvocationTargetException, IllegalAccessException {
        Object instance = null;
        if (method.getReturnType().isAnnotationPresent(Logged.class)) {
            instance = wrapWithLoggingProxy(method.invoke(configuration, objects));
        } else {
            instance = method.invoke(configuration, objects);
        }
        return instance;
    }

    private Object wrapWithLoggingProxy(Object object) {
        return new LoggingManager((HelperService) object);
    }

    public <T> T getInstance(Class<T> clazz) {
        return (T) Optional.ofNullable(instances.get(clazz)).orElseThrow(() -> new NoSuchElementException("Not found"));
    }
}
