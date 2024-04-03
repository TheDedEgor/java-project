package edu.java.scrapper.configuration;

import edu.java.scrapper.models.dto.UpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfiguration {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(applicationConfig.topicName())
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public ProducerFactory<String, UpdateRequest> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.bootstrapServer());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, UpdateRequest> kafkaTemplate(ProducerFactory<String, UpdateRequest> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
