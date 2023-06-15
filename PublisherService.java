package com.akash.moviebookingapp.service;

import org.springframework.kafka.core.KafkaTemplate;

public interface PublisherService {
    public KafkaTemplate<String,String> getTemp();
    public void setTemp(String message);
}
