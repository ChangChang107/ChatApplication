package com.calmero.kafka.producer;

public interface KafkaProducer<T, R> {
    R send(String traceId, T t);
}
