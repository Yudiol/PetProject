package com.yudiol.repository;

import com.yudiol.annotation.Logged;

public interface HelperRepository {

    @Logged(message = "Случайная фраза взята из базы данных")
    String findById(Integer id);

    @Logged(message = "Новая фраза добавлена в базу данных")
    void addPhrase(String phrase);

    Integer getSize();
}
