package com.MessageBroker.Impl;

import com.MessageBroker.MessageBroker;
import com.MessageBroker.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherImpl implements Publisher {

    private final MessageBroker queue;

    public void publishMessage(String message) {
        queue.publish(message);
    }
}
