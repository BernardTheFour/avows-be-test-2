package com.betest.avows.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopic {
    @Value("${spring.kafka.topics.contact}")
    private String CONTACT_TOPIC;

    
    @Value("${spring.kafka.topics.department}")
    private String DEPARTMENT_TOPIC;

    public String getTopic(TopicEnum topicEnum) {
        switch (topicEnum) {
            case CONTACT:
                return CONTACT_TOPIC;
            case DEPARTMENT:
                return DEPARTMENT_TOPIC;
            default:
                return null;
        }
    }

    public enum TopicEnum {
        CONTACT,
        DEPARTMENT,
    }
}