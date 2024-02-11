package com.yudiol.service;


import com.yudiol.annotation.Logged;
import com.yudiol.model.Phrase;

@Logged
public interface HelperService {

    Phrase getRandomPhrase();

    void addPhrase(Phrase phrase);
}
