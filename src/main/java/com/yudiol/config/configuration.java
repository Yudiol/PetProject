package com.yudiol.config;

import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import com.yudiol.service.JsonKafkaProducer;
import com.yudiol.service.impl.KafkaServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnProperty(prefix = "kafka-broker", name = "enabled", matchIfMissing = true)
public class configuration {

    @Bean
    @Primary
    public HelperService helperService(HelperRepository helperRepository, JsonKafkaProducer jsonKafkaProducer) {
        return new KafkaServiceImpl(helperRepository, jsonKafkaProducer);
    }
}
