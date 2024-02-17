package com.yudiol.service.impl;

import com.MessageBroker.Publisher;
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

    @Mock
    private  Publisher publisher;

    @InjectMocks
    private HelperServiceImpl helperService;

    Phrase phrase = new Phrase("Hello");

    @Test
    void get_whenInvoked_thenReturnedPhrase() {

        when(helperRepository.getSize()).thenReturn(1);
        when(helperRepository.findById(anyInt())).thenReturn(phrase.text());

        Phrase phrase = helperService.getRandomPhrase();

        assertNotNull(phrase);
        assertEquals(phrase.text(), phrase.text());
        verify(helperRepository, times(1)).findById(anyInt());
    }

    @Test
    void add_whenInvoked_thenAddPhrase() {

        helperService.addPhrase(phrase.text());

        verify(helperRepository, times(1)).addPhrase(phrase.text());
    }

    @Test
    void send_whenInvoked_addMessageToMessageBroker(){

        publisher.publishMessage(phrase.text());

        verify(publisher,times(1)).publishMessage(phrase.text());
    }
}