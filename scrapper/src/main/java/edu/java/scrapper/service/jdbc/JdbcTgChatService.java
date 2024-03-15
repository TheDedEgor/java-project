package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.service.TgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class JdbcTgChatService implements TgChatService {

    @Autowired
    private ChatRepository chatRepository;

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
        chatRepository.remove(tgChatId);
    }
}
