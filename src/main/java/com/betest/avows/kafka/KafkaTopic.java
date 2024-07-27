package com.betest.avows.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopic {
    @Value("${spring.kafka.topics.student}")
    private String STUDENT_TOPIC;

    
    @Value("${spring.kafka.topics.classroom}")
    private String CLASSROOM_TOPIC;

    public String getTopic(TopicEnum topicEnum) {
        switch (topicEnum) {
            case STUDENT:
                return STUDENT_TOPIC;
            case DEPARTMENT:
                return CLASSROOM_TOPIC;
            default:
                return null;
        }
    }

    public enum TopicEnum {
        STUDENT,
        DEPARTMENT,
    }
}