package com.yudiol.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HelperRepository {
    AtomicInteger number = new AtomicInteger(-1);
    Map<Integer, String> phrases = new ConcurrentHashMap<>();


    {
        phrases.put(number.incrementAndGet(), "Всё будет хорошо");
        phrases.put(number.incrementAndGet(), "Ура!!!");
    }

    public String findById(Integer id) {
        return phrases.get(id);
    }

    public void addPhrase(String phrase) {
        phrases.put(number.incrementAndGet(), phrase);
    }

    public Integer getSize() {
        return phrases.size();
    }

}
