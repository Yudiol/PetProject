package com.yudiol.provider;

import com.yudiol.annotation.GetMapping;

import java.lang.reflect.Method;

public class ProviderGet implements Provider {

    public String provider(Method method) {
        return "GET" + method.getAnnotation(GetMapping.class).path();
    }
}
