package com.scopie.authservice.kafka;

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

    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return  new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<
                ConcurrentMessageListenerContainer<String, String>> factory (){
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

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
//        return  props;
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