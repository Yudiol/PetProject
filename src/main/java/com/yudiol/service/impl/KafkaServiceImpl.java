package com.yudiol.service.impl;

import com.yudiol.model.Phrase;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import com.yudiol.service.JsonKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class KafkaServiceImpl implements HelperService {

    private final HelperRepository helperRepository;
    private final JsonKafkaProducer jsonKafkaProducer;

    public Phrase getRandomPhrase() {
        int random = (int) (Math.random() * helperRepository.getSize());
        return new Phrase(helperRepository.findById(random));
    }

    @KafkaListener(topics = "phrase_json", groupId = "MyGroup", autoStartup = "${kafka-broker.enabled}")
    public void addPhraseToDB(Phrase phrase) {
        helperRepository.addPhrase(phrase.text());
    }

    public void addPhrase(Phrase phrase) {
        jsonKafkaProducer.sendMessage(phrase);
    }
}
