package com.yudiol.service.impl;

import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class SupportService implements HelperService{

    private final HelperService helperService;


    public String getRandomPhrase() {
//        int random = (int) (Math.random() * helperRepository.getSize());
//        return helperRepository.findById(random);
        return "Hi " + helperService.getRandomPhrase();
    }

    @Override
    public void addPhrase(String phrase) {

    }
}
