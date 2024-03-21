package edu.java.scrapper.configuration;

import edu.java.scrapper.repository.JpaChatRepository;
import edu.java.scrapper.repository.JpaLinkRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.TgChatService;
import edu.java.scrapper.service.jpa.JpaLinkService;
import edu.java.scrapper.service.jpa.JpaTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public TgChatService tgChatService(JpaChatRepository chatRepository) {
        return new JpaTgChatService(chatRepository);
    }

    @Bean
    public LinkService linkService(JpaLinkRepository linkRepository, JpaChatRepository chatRepository) {
        return new JpaLinkService(linkRepository, chatRepository);
    }
}
