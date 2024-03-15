package edu.java.scrapper.repository;

import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.models.entity.Chat;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chats", (row, item) ->
            new Chat(row.getLong("id"), row.getLong("tg_chat_id")));
    }

    @Transactional
    public void add(Long tgChatId) {
        jdbcTemplate.update("INSERT INTO chats (tg_chat_id) VALUES (?)", tgChatId);
    }

    @Transactional
    public void remove(Long tgChatId) throws NotFoundChatException {
        var optionalChatId = getChatIdByTgChatId(tgChatId);
        if (optionalChatId.isPresent()) {
            var chatId = optionalChatId.get();
            jdbcTemplate.update("DELETE FROM chats_links WHERE chat_id = ?", chatId);
            jdbcTemplate.update("DELETE FROM chats WHERE tg_chat_id = ?", tgChatId);
        } else {
            throw new NotFoundChatException("Not found chat");
        }
    }

    private Optional<Long> getChatIdByTgChatId(Long tgChatId) {
        var ids = jdbcTemplate.query("SELECT id FROM chats WHERE tg_chat_id = ?", (row, item) ->
            row.getLong("id"), tgChatId);
        if (ids.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ids.getFirst());
    }
}
