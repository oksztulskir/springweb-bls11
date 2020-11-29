package com.sda.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    @Test
    void shouldSendMessage() {
        // given
        String to = "mail_to@gmail.com";
        String subject = "My subject";
        String content = "This is a simple test!";
        JavaMailSender sender = Mockito.mock(JavaMailSender.class);
        MailService service = new MailService(sender);
        Mockito.doNothing().when(sender).send(Mockito.any(SimpleMailMessage.class));
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // when
        service.send(to, subject, content);

        // then
        Mockito.verify(sender).send(messageCaptor.capture());
        SimpleMailMessage message = messageCaptor.getValue();
        if (message != null) {
            Assertions.assertEquals(to, message.getTo()[0]);
            Assertions.assertEquals(subject, message.getSubject());
            Assertions.assertEquals(content, message.getText());
            Assertions.assertEquals("oksztul@gmail.com", message.getFrom());
        }
    }
}