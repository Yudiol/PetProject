package com.MessageBroker;

public interface MessageBroker {

    void publish(String message);

    String poll();
}

