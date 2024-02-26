package com.yudiol.service.impl;


import com.yudiol.model.Phrase;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import com.yudiol.service.JsonKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelperServiceImpl implements HelperService {

    private final HelperRepository helperRepository;

    private final JsonKafkaProducer jsonKafkaProducer;


    public Phrase getRandomPhrase() {
        int random = (int) (Math.random() * helperRepository.getSize());
        return new Phrase(helperRepository.findById(random));
    }

    public void addPhrase(Phrase phrase) {
        checkIfPhraseExistInStorage(phrase);
        helperRepository.addPhrase(phrase.text());
    }

    private void checkIfPhraseExistInStorage(Phrase phrase) {
        if (!helperRepository.isExist(phrase.text())) {
            jsonKafkaProducer.sendMessage(phrase);
        }

    }
}
