package com.MessageBroker.Impl;

import com.MessageBroker.MessageBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MessageBrokerImpl implements MessageBroker {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void publish(String message){
        queue.offer(message);
    }

    public String poll() {
        return queue.poll();
    }
}

