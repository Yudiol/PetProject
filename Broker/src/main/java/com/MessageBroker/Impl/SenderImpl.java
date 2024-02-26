package com.MessageBroker.Impl;

import com.MessageBroker.CreatorOfSender;
import com.MessageBroker.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SenderImpl implements Sender {

    private final CreatorOfSender creatorOfSender;

    public void passMessageToAnnotatedMethod(String message) {

        Map<Object, List<Method>> mapMethods = creatorOfSender.getMapMethods();

        for (Object object : mapMethods.keySet()) {
            for (Method method : mapMethods.get(object)) {
                try {
                    method.invoke(object, message);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}