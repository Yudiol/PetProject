package com.yudiol.service.impl;

import com.yudiol.model.Phrase;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class HelperServiceImpl implements HelperService {

    private final HelperRepository helperRepository;

    public Phrase getRandomPhrase() {
        int random = (int) (Math.random() * helperRepository.getSize());
        return new Phrase(helperRepository.findById(random));
    }

    public void addPhrase(Phrase phrase) {
        helperRepository.addPhrase(phrase.text());
    }
}
