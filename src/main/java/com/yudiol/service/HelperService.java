package com.yudiol.service;


import com.yudiol.model.Phrase;

public interface HelperService {

    Phrase getRandomPhrase();
  
    void addPhrase(Phrase phrase);

}
