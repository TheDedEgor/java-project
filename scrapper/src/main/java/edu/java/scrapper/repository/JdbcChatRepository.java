package edu.java.scrapper.repository;

import edu.java.scrapper.models.domain.Chat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("MultipleStringLiterals")
public class JdbcChatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chats", (row, item) ->
            new Chat(row.getLong("id"), row.getLong("tg_chat_id")));
    }

    public Boolean existByTgChatId(Long tgChatId) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM chats WHERE tg_chat_id = ?)",
            Boolean.class, tgChatId
        );
    }

    @Transactional
    public Long add(Long tgChatId) {
        var insertIntoLink = new SimpleJdbcInsert(jdbcTemplate).withTableName("chats").usingGeneratedKeyColumns("id");
        final var parameters = Map.of("tg_chat_id", tgChatId);
        return (Long) insertIntoLink.executeAndReturnKey(parameters);
    }

    @Transactional
    public void remove(Long tgChatId) {
        jdbcTemplate.update("DELETE FROM chats WHERE tg_chat_id = ?", tgChatId);
    }
}
