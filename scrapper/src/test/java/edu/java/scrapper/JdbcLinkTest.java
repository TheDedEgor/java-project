package edu.java.scrapper;

import edu.java.scrapper.repository.JdbcChatRepository;
import edu.java.scrapper.repository.JdbcLinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JdbcLinkTest extends IntegrationTest {

    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    public void addLink()  {
        var chatId = jdbcChatRepository.add(1L);
        jdbcLinkRepository.add(chatId, "https://edu.tinkoff.ru");
        var links = jdbcLinkRepository.findAll(chatId);
        assertThat(links.size()).isEqualTo(1L);
        var link = links.getFirst();
        assertThat(link.id()).isEqualTo(1L);
        assertThat(link.url()).isEqualTo("https://edu.tinkoff.ru");
    }

    @Test
    @Transactional
    @Rollback
    public void removeLink() {
        var chatId = jdbcChatRepository.add(1L);
        var link = jdbcLinkRepository.add(chatId, "https://edu.tinkoff.ru");
        jdbcLinkRepository.remove(chatId, link.id());
        var links = jdbcLinkRepository.findAll(1L);
        assertThat(links.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll() {
        var chatId = jdbcChatRepository.add(1L);
        jdbcLinkRepository.add(chatId, "https://edu.tinkoff.ru");
        jdbcLinkRepository.add(chatId, "https://docs.spring.io");
        var links = jdbcLinkRepository.findAll(chatId);
        assertThat(links.size()).isEqualTo(2);
    }
}
