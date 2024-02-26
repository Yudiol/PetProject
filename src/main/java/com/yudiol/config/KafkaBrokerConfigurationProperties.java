package com.yudiol.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("kafka-broker")
public record KafkaBrokerConfigurationProperties(
        boolean enabled
) {
}
