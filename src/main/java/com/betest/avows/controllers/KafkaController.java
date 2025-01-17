package com.betest.avows.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.kafka.KafkaProducer;
import com.betest.avows.kafka.KafkaTopic;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/contact")
    public ResponseEntity<String> addContact(@RequestBody ContactDto contactDto) {
        kafkaProducer.sendMessage(
                KafkaTopic.TopicEnum.CONTACT,
                contactDto);

        return ResponseEntity.ok("Kafka message produced");
    }
}
