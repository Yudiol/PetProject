package com.yudiol.service.impl;

import com.MessageBroker.Publisher;
import com.MessageBroker.annotation.Subscriber;
import com.yudiol.model.Phrase;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HelperServiceImpl implements HelperService {

    private final HelperRepository helperRepository;
    private final Publisher publisher;

    public Phrase getRandomPhrase() {
        int random = (int) (Math.random() * helperRepository.getSize());
        return new Phrase(helperRepository.findById(random));
    }

    public void sendPhraseToBroker(Phrase phrase) {
        publisher.publishMessage(phrase.text());
    }

    @Subscriber
    public void addPhrase(String message) {
        helperRepository.addPhrase(message);
    }
}
