package edu.java.scrapper.service;

import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;

public interface TgChatService {
    void register(Long tgChatId) throws ExistChatException;

    void unregister(Long tgChatId) throws NotFoundChatException;
}
