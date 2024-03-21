package edu.java.scrapper;

import edu.java.scrapper.models.entity.Chat;
import edu.java.scrapper.models.entity.Link;
import edu.java.scrapper.repository.JpaChatRepository;
import edu.java.scrapper.repository.JpaLinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpaLinkTest extends IntegrationTest {

    @Autowired
    private JpaLinkRepository linkRepository;

    @Autowired
    private JpaChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addLink() {
        var chat = new Chat(1L);
        chat = chatRepository.save(chat);
        var link = new Link("https://edu.tinkoff.ru", chat);
        linkRepository.save(link);
        chat = chatRepository.findByTgChatId(1L).get();
        var links = chat.getLinks();
        assertThat(links.size()).isEqualTo(1L);
        link = links.getFirst();
        assertThat(link.getId()).isEqualTo(1L);
        assertThat(link.getUrl()).isEqualTo("https://edu.tinkoff.ru");
    }

    @Test
    @Transactional
    @Rollback
    public void removeLink() {
        var chat = new Chat(1L);
        chat = chatRepository.save(chat);
        var link = new Link("https://edu.tinkoff.ru", chat);
        link = linkRepository.save(link);
        chat.getLinks().remove(link);
        chatRepository.save(chat);
        chat = chatRepository.findByTgChatId(1L).get();
        var links = chat.getLinks();
        assertThat(links.size()).isEqualTo(0);
    }
}
