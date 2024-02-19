package com.yudiol.repository.Impl;

import com.yudiol.annotation.Logged;
import com.yudiol.repository.HelperRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HelperRepositoryImpl implements HelperRepository {
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
