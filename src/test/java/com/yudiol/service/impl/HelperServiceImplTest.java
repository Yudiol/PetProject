package com.yudiol.service.impl;

import com.yudiol.model.Phrase;
import com.yudiol.repository.HelperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelperServiceImplTest {

    @Mock
    private HelperRepository helperRepository;

    @InjectMocks
    private HelperServiceImpl helperService;

    @Test
    void get_whenInvoked_thenReturnedPhrase() {

        when(helperRepository.getSize()).thenReturn(1);
        when(helperRepository.findById(anyInt())).thenReturn("Hello");

        Phrase phrase = helperService.getRandomPhrase();

        assertNotNull(phrase);
        assertEquals("Hello", phrase.text());
        verify(helperRepository, times(1)).findById(anyInt());
    }

    @Test
    void add_whenInvoked_thenAddPhrase() {

        helperService.addPhrase(new Phrase("Hello"));

        verify(helperRepository, times(1)).addPhrase("Hello");
    }
}