package edu.java.bot.repository.links;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.models.dto.AddLinkRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("InDbLinkRepository")
public class InDbLinkRepository implements LinkRepository {

    @Autowired
    private ScrapperClient scrapperClient;

    @Override
    public List<String> getLinks(Long chatId) {
        var linksResponse = scrapperClient.getLinks(chatId);
        return linksResponse.links().stream()
            .map(link -> link.url().toString())
            .toList();
    }

    @Override
    public void trackLink(Long chatId, String link) {
        scrapperClient.createLink(chatId, new AddLinkRequest(link));
    }

    @Override
    public void untrackLink(Long chatId, String link) {
        scrapperClient.deleteLink(chatId, new AddLinkRequest(link));
    }
}
