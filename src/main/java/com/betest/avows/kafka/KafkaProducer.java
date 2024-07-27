package com.betest.avows.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaTopic kafkaTopic;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, KafkaTopic kafkaTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
    }

    public void sendMessage(KafkaTopic.TopicEnum topicEnum, Object message) {
        logger.info("KAFKA PRODUCE - (" + kafkaTopic.getTopic(topicEnum) + ") " + message);
        kafkaTemplate.send(kafkaTopic.getTopic(topicEnum), message);
    }
}
