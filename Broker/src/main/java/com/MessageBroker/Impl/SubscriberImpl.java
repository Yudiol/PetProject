package com.MessageBroker.Impl;

import com.MessageBroker.MessageBroker;
import com.MessageBroker.Sender;
import com.MessageBroker.Subscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriberImpl implements Subscriber {

    private final MessageBroker queue;
    private final Sender sender;

    @PostConstruct
    public void init() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            String message = queue.poll();
            if (message != null) {
                sender.passMessageToAnnotatedMethod(message);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}
