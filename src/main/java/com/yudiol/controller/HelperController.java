package com.yudiol.controller;

import com.yudiol.model.Phrase;
import com.yudiol.service.HelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/help-service/v1/support")
public class HelperController {
    private final HelperService helperService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void add(@RequestBody Phrase phrase) {
        helperService.sendPhraseToBroker(phrase);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Phrase get() {
        return helperService.getRandomPhrase();
    }
}
