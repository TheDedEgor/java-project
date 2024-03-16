package edu.java.bot.repository.chat;

import edu.java.bot.client.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InDbChatRepository implements ChatRepository {

    @Autowired
    private ScrapperClient scrapperClient;

    @Override
    public void registerChat(Long id) {
        scrapperClient.createTgChat(id);
    }

    @Override
    public void unregisterChat(Long id) {
        scrapperClient.deleteTgChat(id);
    }
}
