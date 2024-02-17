package com.yudiol.util;

import com.yudiol.annotation.Logged;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Logged.class)) {
            Logged annotation = method.getAnnotation(Logged.class);
            System.out.println(annotation.message());
        }
        return method.invoke(target, args);
    }
}
