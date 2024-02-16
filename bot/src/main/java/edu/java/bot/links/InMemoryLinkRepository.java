package edu.java.bot.links;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryLinkRepository implements LinkRepository {

    public static Map<Long, List<String>> mapUrl = new HashMap<>();

    @Override
    public List<String> getLinks(Long chatId) {
        return mapUrl.getOrDefault(chatId, new ArrayList<>());
    }

    @Override
    public Boolean existLink(Long chatId, String link) {
        var urls = mapUrl.getOrDefault(chatId, new ArrayList<>());
        return urls.contains(link);
    }

    @Override
    public Boolean trackLink(Long chatId, String link) {
        var urls = mapUrl.getOrDefault(chatId, new ArrayList<>());
        if (urls.isEmpty()) {
            urls.add(link);
            mapUrl.put(chatId, urls);
            return true;
        } else if (!urls.contains(link)) {
            urls.add(link);
            return true;
        }
        return false;
    }

    @Override
    public Boolean untrackLink(Long chatId, String link) {
        var urls = mapUrl.getOrDefault(chatId, new ArrayList<>());
        return urls.remove(link);
    }
}
