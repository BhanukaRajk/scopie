package com.scopie.authservice.kafka;

import com.scopie.authservice.kafka.dto.KafkaReservationDTO;
import com.scopie.authservice.kafka.dto.KafkaReservedSeatDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducer {

    @Value("${spring.kafka.bootstrap-servers}") // KAFKA SERVER PATH
    private String bootstrapServers;


    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, Long> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String, Long> kafkaTemplate(ProducerFactory<String, Long> reservationCancelProducerFactory) {
        return new KafkaTemplate<>(reservationCancelProducerFactory);
    } // RESERVATION CANCELLER


    @Bean
    public ProducerFactory<String, KafkaReservationDTO> reservationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String, KafkaReservationDTO> reservationKafkaTemplate(ProducerFactory<String, KafkaReservationDTO> reservationProducerFactory) {
        return new KafkaTemplate<>(reservationProducerFactory);
    } // NEW RESERVATION CREATOR


    @Bean
    public ProducerFactory<String, KafkaReservedSeatDTO> reservedSeatProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String, KafkaReservedSeatDTO> reservedSeatKafkaTemplate(ProducerFactory<String, KafkaReservedSeatDTO> reservedSeatProducerFactory) {
        return new KafkaTemplate<>(reservedSeatProducerFactory);
    } // SEAT RESERVATION CREATOR
}