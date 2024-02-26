package com.MessageBroker.Impl;

import com.MessageBroker.CreatorOfSender;
import com.MessageBroker.annotation.Subscriber;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CreatorOfSenderImpl implements CreatorOfSender {

    private final Set<Object> objects;

    private final Map<Object, List<Method>> mapMethods = new ConcurrentHashMap<>();

    public CreatorOfSenderImpl(Set<Object> objects) {
        this.objects = objects;
        getAnnotatedMethods();
    }

    private void getAnnotatedMethods() {
        for (Object bean : objects) {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getMethods();
            List<Method> methodList = new ArrayList<>();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Subscriber.class)) {
                    methodList.add(method);
                }
            }
            mapMethods.put(bean, methodList);
        }
    }

    public Map<Object, List<Method>> getMapMethods() {
        return mapMethods;
    }
}
