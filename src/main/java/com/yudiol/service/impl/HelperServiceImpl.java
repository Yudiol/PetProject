package com.yudiol.service.impl;

import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HelperServiceImpl implements HelperService {

    private final HelperRepository helperRepository;

    public String getRandomPhrase() {
        int random = (int) (Math.random() * helperRepository.getSize());
        return helperRepository.findById(random);
    }

    public void addPhrase(String phrase) {
        helperRepository.addPhrase(phrase);
    }

}
