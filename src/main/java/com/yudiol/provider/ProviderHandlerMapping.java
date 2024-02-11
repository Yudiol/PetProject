package com.yudiol.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProviderHandlerMapping {

    private final Map<String, Provider> providers = new ConcurrentHashMap<>();

    public ProviderHandlerMapping() {
        providers.put("GetMapping", new ProviderGet());
        providers.put("PostMapping", new ProviderPost());
    }

    public String getProvider(Annotation[] annotations, Method method) {
        List<String> listAnnotations = Arrays.stream(annotations).map(an -> an.annotationType().getName())
                .map(e -> e.substring(e.lastIndexOf(".") + 1)).toList();

        for (String key : listAnnotations) {
            if (providers.containsKey(key)) {
                return providers.get(key).provider(method);
            }
        }
        return null;
    }
}
