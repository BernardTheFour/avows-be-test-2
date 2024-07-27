package com.betest.avows.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.betest.avows.dtos.StudentDto;
import com.betest.avows.services.StudentService;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final StudentService studentService;

    public KafkaConsumer(StudentService studentService) {
        this.studentService = studentService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.student}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "studentListenerContainerFactory")
    public void consumeStudent(StudentDto studentDto) {
        logger.info("KAFKA CONSUME - (student) " + studentDto);
        studentService.saveStudent(studentDto);
    }
}
