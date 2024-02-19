package com.MessageBroker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public interface CreatorOfSender {

    Map<Object, List<Method>> getMapMethods();
}
