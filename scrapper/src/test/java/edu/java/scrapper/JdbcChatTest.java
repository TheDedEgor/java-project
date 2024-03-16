package edu.java.scrapper;

import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JdbcChatTest extends IntegrationTest {

    @Autowired
    private ChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        chatRepository.add(1L);
        var chats = chatRepository.findAll();
        assertThat(chats.size()).isEqualTo(1);
        var chat = chats.getFirst();
        assertThat(chat.tgChatId()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() throws NotFoundChatException {
        chatRepository.add(1L);
        chatRepository.remove(1L);
        var chats = chatRepository.findAll();
        assertThat(chats.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        chatRepository.add(1L);
        chatRepository.add(2L);
        var chats = chatRepository.findAll();
        assertThat(chats.size()).isEqualTo(2);
        for (int i = 0; i < 2; i++) {
            var chat = chats.get(i);
            assertThat(chat.tgChatId()).isEqualTo(i + 1);
        }
    }
}
