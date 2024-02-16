package edu.java.bot.links;

import java.util.List;

public interface LinkRepository {

    List<String> getLinks(Long chatId);

    Boolean existLink(Long chatId, String link);

    Boolean trackLink(Long chatId, String link);

    Boolean untrackLink(Long chatId, String link);
}
