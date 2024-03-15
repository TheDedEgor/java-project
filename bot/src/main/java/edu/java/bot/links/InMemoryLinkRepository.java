package edu.java.bot.links;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("InMemoryLinkRepository")
public class InMemoryLinkRepository implements LinkRepository {

    public static Map<Long, List<String>> mapUrl = new HashMap<>();

    @Override
    public List<String> getLinks(Long chatId) {
        return mapUrl.getOrDefault(chatId, new ArrayList<>());
    }


    @Override
    public void trackLink(Long chatId, String link) {
        var urls = mapUrl.getOrDefault(chatId, new ArrayList<>());
        if (urls.isEmpty()) {
            urls.add(link);
            mapUrl.put(chatId, urls);
        } else if (!urls.contains(link)) {
            urls.add(link);
        }
    }

    @Override
    public void untrackLink(Long chatId, String link) {
        mapUrl.getOrDefault(chatId, new ArrayList<>());
    }
}
