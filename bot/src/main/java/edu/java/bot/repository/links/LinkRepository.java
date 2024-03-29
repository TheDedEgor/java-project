package edu.java.bot.repository.links;

import java.util.List;

public interface LinkRepository {

    List<String> getLinks(Long chatId);

    void trackLink(Long chatId, String link);

    void untrackLink(Long chatId, String link);
}
