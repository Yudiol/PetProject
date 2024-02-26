package com.yudiol.util;

import com.yudiol.repository.HelperRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingBeanPostProcessor implements BeanPostProcessor {

    Map<String, Object> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof HelperRepository) {
            map.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object originalBean = map.get(beanName);

        if (originalBean != null) {
            return Proxy.newProxyInstance(
                    originalBean.getClass().getClassLoader(),
                    originalBean.getClass().getInterfaces(),
                    new LoggingInvocationHandler(originalBean)
            );
        }
        return bean;
    }
}
