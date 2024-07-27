package com.betest.avows.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.services.ContactService;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ContactService contactService;

    public KafkaConsumer(ContactService contactService) {
        this.contactService = contactService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.contact}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "contactListenerContainerFactory")
    public void consumeContact(ContactDto contactDto) {
        logger.info("KAFKA CONSUME - (student) " + contactDto);
        contactService.saveContact(contactDto);
    }
}
