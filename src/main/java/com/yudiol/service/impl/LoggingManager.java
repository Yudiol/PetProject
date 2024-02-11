package com.yudiol.service.impl;

import com.yudiol.model.Phrase;
import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LoggingManager implements HelperService {

    private final HelperService helperService;
    @Override
    public Phrase getRandomPhrase() {
        System.out.println("Получена случайная фраза");
        return helperService.getRandomPhrase();
    }

    @Override
    public void addPhrase(Phrase phrase) {
        System.out.println("Новая фраза добавлена в базу");
        helperService.addPhrase(phrase);
    }
}
