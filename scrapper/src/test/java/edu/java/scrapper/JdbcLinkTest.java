package edu.java.scrapper;

import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JdbcLinkTest extends IntegrationTest {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addLink()  {
        var chatId = chatRepository.add(1L);
        linkRepository.add(chatId, "https://edu.tinkoff.ru");
        var links = linkRepository.findAll(chatId);
        assertThat(links.size()).isEqualTo(1L);
        var link = links.getFirst();
        assertThat(link.id()).isEqualTo(1L);
        assertThat(link.url()).isEqualTo("https://edu.tinkoff.ru");
    }

    @Test
    @Transactional
    @Rollback
    public void removeLink() {
        var chatId = chatRepository.add(1L);
        var link = linkRepository.add(chatId, "https://edu.tinkoff.ru");
        linkRepository.remove(chatId, link.id());
        var links = linkRepository.findAll(1L);
        assertThat(links.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll() {
        var chatId = chatRepository.add(1L);
        linkRepository.add(chatId, "https://edu.tinkoff.ru");
        linkRepository.add(chatId, "https://docs.spring.io");
        var links = linkRepository.findAll(chatId);
        assertThat(links.size()).isEqualTo(2);
    }
}