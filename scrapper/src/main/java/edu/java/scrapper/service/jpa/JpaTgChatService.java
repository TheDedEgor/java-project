package edu.java.scrapper.service.jpa;

import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.models.entity.Chat;
import edu.java.scrapper.repository.JpaChatRepository;
import edu.java.scrapper.service.TgChatService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

public class JpaTgChatService implements TgChatService {

    private final JpaChatRepository chatRepository;

    public JpaTgChatService(JpaChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(Long tgChatId) throws ExistChatException {
        try {
            var chat = new Chat(tgChatId);
            chatRepository.save(chat);
        } catch (DuplicateKeyException ex) {
            throw new ExistChatException("The chat already exists");
        }
    }

    @Override
    @Transactional
    public void unregister(Long tgChatId) throws NotFoundChatException {
        var optionalChat = chatRepository.findByTgChatId(tgChatId);
        if (optionalChat.isPresent()) {
            chatRepository.deleteByTgChatId(tgChatId);
        } else {
            throw new NotFoundChatException("Not found chat");
        }
    }
}
