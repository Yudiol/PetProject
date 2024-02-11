package com.yudiol.provider;

import com.yudiol.annotation.PostMapping;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class ProviderPost implements Provider {

    @Override
    public String provider(Method method) {
        return "POST" + method.getAnnotation(PostMapping.class).path();
    }
}
