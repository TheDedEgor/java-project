package edu.java.scrapper.configuration;

import edu.java.scrapper.repository.JdbcChatRepository;
import edu.java.scrapper.repository.JdbcLinkRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.TgChatService;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import edu.java.scrapper.service.jdbc.JdbcTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public TgChatService tgChatService(JdbcChatRepository chatRepository) {
        return new JdbcTgChatService(chatRepository);
    }

    @Bean
    public LinkService linkService(JdbcLinkRepository linkRepository) {
        return new JdbcLinkService(linkRepository);
    }
}
