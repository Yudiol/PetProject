package com.yudiol.controller;

import com.yudiol.model.Phrase;
import com.yudiol.service.HelperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelperControllerTest {

    @Mock
    private HelperService helperService;

    @InjectMocks
    private HelperController helperController;

    private Phrase phrase = null;

    @BeforeEach
    public void setUp() {
        phrase = new Phrase("Hello");
    }

    @Test
    public void add_whenInvoked_thenAddPhrase() {

        helperController.add(phrase);

        verify(helperService, times(1)).addPhrase(phrase);
    }

    @Test
    public void get_whenInvoked_thenReturnedPhrase() {

        when(helperService.getRandomPhrase()).thenReturn(phrase);

        Phrase result = helperController.get();

        assertNotNull(result);
        assertEquals(phrase.text(), result.text());
        verify(helperService, times(1)).getRandomPhrase();
    }
}