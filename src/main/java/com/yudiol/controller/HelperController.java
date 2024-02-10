package com.yudiol.controller;

import com.yudiol.annotation.GetMapping;
import com.yudiol.annotation.PostMapping;
import com.yudiol.annotation.RestController;
import com.yudiol.model.Phrase;
import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class HelperController {

    private final HelperService helperService;

    @PostMapping(path = "/help-service/v1/support")
    public void create(Phrase phrase) {
        helperService.addPhrase(phrase.text());
    }

    @GetMapping(path = "/help-service/v1/support")
    public Phrase get() {
        return new Phrase(helperService.getRandomPhrase());
    }
}