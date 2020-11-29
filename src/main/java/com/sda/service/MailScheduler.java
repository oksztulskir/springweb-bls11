package com.sda.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 */
@Slf4j
@Component
public class MailScheduler {
    private final MailService mailService;

    public MailScheduler(MailService mailService) {
        this.mailService = mailService;
    }

//    @Scheduled(fixedRate = 30 * 1000)
    public void sendMail() {
        log.info("Sending email ...");
        mailService.send("oksztul@gmail.com", "This is a simple test!", "Test!!");
    }
}
