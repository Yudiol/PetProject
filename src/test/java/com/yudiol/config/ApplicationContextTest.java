package com.yudiol.config;

import com.yudiol.configuration.ApplicationContext;
import com.yudiol.service.impl.SupportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class ApplicationContextTest {

    @Test
    public void should_return_instance_by_class() throws InvocationTargetException, IllegalAccessException {
        ApplicationContext context = new ApplicationContext();
        Assertions.assertEquals(SupportService.class, context.getInstance(SupportService.class).getClass());
    }

}