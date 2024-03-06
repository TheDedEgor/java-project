package edu.java.scrapper;

import edu.java.repository.ChatRepository;
import org.junit.Test;
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
        chatRepository.add(1);
        var chats = chatRepository.findAll();
        assertThat(chats.size()).isEqualTo(1);
    }
}
