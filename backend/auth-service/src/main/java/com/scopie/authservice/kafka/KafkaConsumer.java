package com.scopie.authservice.kafka;

import com.scopie.authservice.kafka.dto.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumer {

    @Value("${spring.kafka.bootstrap-servers}") // KAFKA SERVER PATH
    private String bootstrapServers;

//    public Map<String, Object> consumerConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return props;
//    }
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, Long> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<
                ConcurrentMessageListenerContainer<String, Long>> factory(){
        ConcurrentKafkaListenerContainerFactory<String, Long> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    } // CANCEL THE RESERVATION... TODO: NOT REQUIRED

    @Bean
    public ConsumerFactory<String, KafkaReservationDTO> reservationConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaReservationDTO>> reservationFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaReservationDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reservationConsumerFactory());
        return factory;
    }// CREATE NEW RESERVATION... TODO: NOT REQUIRED

    @Bean
    public ConsumerFactory<String, KafkaReservedSeatDTO> reservedSeatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaReservedSeatDTO>> reservedSeatFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaReservedSeatDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reservedSeatConsumerFactory());
        return factory;
    } // RESERVED SEATS... TODO: NOT REQUIRED

    @Bean
    public ConsumerFactory<String, KafkaCinemaDTO> cinemaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaCinemaDTO>> cinemaFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaCinemaDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cinemaConsumerFactory());
        return factory;
    } // CINEMA CREATION

    @Bean
    public ConsumerFactory<String, KafkaMovieDTO> movieConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaMovieDTO>> movieFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMovieDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(movieConsumerFactory());
        return factory;
    } // MOVIE UPDATER

    @Bean
    public ConsumerFactory<String, KafkaMovieTimeDTO> movieTimeConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaMovieTimeDTO>> movieTimeFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMovieTimeDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(movieTimeConsumerFactory());
        return factory;
    } // MOVIE TIME UPDATER

}

//
//package com.cinema.authservice.kafka;
//
//        import com.cinema.authservice.dto.MovieDto;
//        import org.apache.kafka.clients.consumer.ConsumerConfig;
//        import org.apache.kafka.common.serialization.StringDeserializer;
//        import org.apache.kafka.common.serialization.StringSerializer;
//        import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//        import org.springframework.kafka.config.KafkaListenerContainerFactory;
//        import org.springframework.kafka.core.ConsumerFactory;
//        import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//        import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//        import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//        import java.util.HashMap;
//        import java.util.Map;
//
//@Configuration
//public class KafkaConsumerConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private  String bootstrapServers;
//
//    public Map<String,Object> consumerConfig(){
//        Map<String,Object> props=new HashMap<>();
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        return props;
//    }
//    @Bean
//    public ConsumerFactory<String, MovieDto> consumerFactory(){
//        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
//                new JsonDeserializer<>(MovieDto.class, false));
//    }
//
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,MovieDto>> factory(
//            ConsumerFactory<String, MovieDto> consumerFactory
//    ){
//        ConcurrentKafkaListenerContainerFactory<String,MovieDto> factory=new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//
//
//}