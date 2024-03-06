package edu.java.repository;

import edu.java.models.entity.Chat;
import java.util.List;
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
            new Chat(row.getLong("id"), row.getLong("chat_id")));
    }

    @Transactional
    public void add(Integer id) {
        jdbcTemplate.update("INSERT INTO chats (chat_id) VALUES (?)", id);
    }

    @Transactional
    public void remove(Integer id) {
        jdbcTemplate.update("DELETE FROM chats WHERE chat_id = ?", id);
    }
}
