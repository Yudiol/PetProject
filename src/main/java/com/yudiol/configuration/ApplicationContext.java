package com.yudiol.configuration;

import com.yudiol.annotation.Configuration;
import com.yudiol.annotation.Instance;
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
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
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
                instances.put(methodsWithoutParam.getReturnType(), methodsWithoutParam.invoke(configuration));
            }

            for (Method methodsWithParam : methodsWithParams) {
                Object[] objects = Arrays.stream(methodsWithParam.getParameters())
                        .map(param -> instances.get(param.getType()))
                        .toArray();
                instances.put(methodsWithParam.getReturnType(), methodsWithParam.invoke(configuration, objects));
            }
        }
    }

    public <T> T getInstance(Class<T> clazz) {
        return (T) Optional.ofNullable(instances.get(clazz)).orElseThrow(() -> new NoSuchElementException("Not found"));
    }
}
