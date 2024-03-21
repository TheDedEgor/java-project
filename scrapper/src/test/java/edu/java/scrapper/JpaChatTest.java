package edu.java.scrapper;

import edu.java.scrapper.models.entity.Chat;
import edu.java.scrapper.repository.JpaChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpaChatTest extends IntegrationTest {

    @Autowired
    private JpaChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        var chat = new Chat(1L);
        chatRepository.save(chat);
        var chats = chatRepository.findAll();
        assertThat(chats.size()).isEqualTo(1);
        chat = chats.getFirst();
        assertThat(chat.getTgChatId()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        var chat = new Chat(1L);
        chat = chatRepository.save(chat);
        chatRepository.deleteById(chat.getId());
        var chats = chatRepository.findAll();
        assertThat(chats.size()).isEqualTo(0);
    }
}
