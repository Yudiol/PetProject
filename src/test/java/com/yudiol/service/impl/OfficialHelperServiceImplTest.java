package com.yudiol.service.impl;

import com.yudiol.config.ApplicationContext;
import com.yudiol.service.HelperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class OfficialHelperServiceImplTest {

    @Test
    void getRandomPhrase() throws InvocationTargetException, IllegalAccessException {
        ApplicationContext context = new ApplicationContext();
        HelperService helperService = context.getInstance(SupportService.class);
        Assertions.assertEquals("Hi Hello", helperService.getRandomPhrase());
    }
}