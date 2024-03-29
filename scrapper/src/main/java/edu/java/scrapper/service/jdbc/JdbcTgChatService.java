package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.repository.JdbcChatRepository;
import edu.java.scrapper.service.TgChatService;
import org.springframework.dao.DuplicateKeyException;

public class JdbcTgChatService implements TgChatService {

    private final JdbcChatRepository chatRepository;

    public JdbcTgChatService(JdbcChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(Long tgChatId) throws ExistChatException {
        try {
            chatRepository.add(tgChatId);
        } catch (DuplicateKeyException ex) {
            throw new ExistChatException("The chat already exists");
        }
    }

    @Override
    public void unregister(Long tgChatId) throws NotFoundChatException {
        var exist = chatRepository.existByTgChatId(tgChatId);
        if (exist) {
            chatRepository.remove(tgChatId);
        } else {
            throw new NotFoundChatException("Not found chat");
        }
    }
}
