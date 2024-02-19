package com.yudiol.config;

import com.MessageBroker.CreatorOfSender;
import com.MessageBroker.Impl.CreatorOfSenderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Configuration
@ComponentScan(basePackages = "com.MessageBroker")
@RequiredArgsConstructor
public class configuration {

    private final ApplicationContext applicationContext;

    @Bean
    public CreatorOfSender creatorOfSender(Object object) {
        var objects = new HashSet<>(applicationContext.getBeansWithAnnotation(Service.class).values());
        return new CreatorOfSenderImpl(objects);
    }
}
