package edu.java.scrapper.repository;

import edu.java.scrapper.models.entity.Chat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    void deleteByTgChatId(Long tgChatId);

    Optional<Chat> findByTgChatId(Long tgChatId);
}
