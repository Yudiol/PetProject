package com.yudiol.repository;

public interface HelperRepository {

    String findById(Integer id);

    void addPhrase(String phrase);

    Integer getSize();

    boolean isExist(String phrase);

}
