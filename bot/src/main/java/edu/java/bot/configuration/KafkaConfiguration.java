package edu.java.bot.configuration;

import edu.java.bot.models.dto.UpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration public class KafkaConfiguration {

    @Autowired private ApplicationConfig applicationConfig;

    @Bean public NewTopic topic() {
        return TopicBuilder.name(applicationConfig.topicName()).partitions(1).replicas(1).build();
    }

    @Bean public ConsumerFactory<String, UpdateRequest> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.bootstrapServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, applicationConfig.consumerGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, UpdateRequest.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean public ConcurrentKafkaListenerContainerFactory<String, UpdateRequest> containerFactory(
        ConsumerFactory<String, UpdateRequest> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, UpdateRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean public ProducerFactory<String, UpdateRequest> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.bootstrapServer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, UpdateRequest> retryableTopicKafkaTemplate(
        ProducerFactory<String, UpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
