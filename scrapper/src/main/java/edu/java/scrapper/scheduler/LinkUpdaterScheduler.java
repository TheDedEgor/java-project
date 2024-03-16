package edu.java.scrapper.scheduler;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.models.domain.Link;
import edu.java.scrapper.models.dto.UpdateRequest;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Log4j2
@Component
public class LinkUpdaterScheduler {

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private StackOverflowClient stackOverflowClient;

    @Autowired
    private BotClient botClient;

    @Autowired
    private LinkService linkService;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        var links = linkService.findOldUpdateLinks();
        links.forEach(this::checkLinks);
    }

    private void checkLinks(Link link) {
        try {
            var url = new URI(link.url());
            var host = url.getHost();
            if (host.equals("github.com")) {
                gitHabHandler(link, url);
            } else if (host.equals("stackoverflow.com")) {
                stackOverflowHandler(link, url);
            }
        } catch (URISyntaxException ex) {
            log.error(ex.getMessage());
        }
    }

    private void gitHabHandler(Link link, URI url) {
        var path = url.getPath().split("/");
        var owner = path[1];
        var repo = path[2];
        var res = gitHubClient.getRepository(owner, repo);
        if (link.lastCheckTime().isBefore(res.updatedAt())
            || link.lastCheckTime().isBefore(res.pushedAt())) {
            linkService.updateLinkDate(link.id());
            var tgChatIds = linkService.getAllTgChatIdByLinkId(link.id());
            sendBotUpdates(link, "Репозиторий GitHub обновился", tgChatIds);
        }
    }

    private void stackOverflowHandler(Link link, URI url) {
        var questionId = Long.parseLong(url.getPath().split("/")[2]);
        var res = stackOverflowClient.getQuestion(questionId).questions().getFirst();
        if (link.lastCheckTime().isBefore(res.lastActivityDate())) {
            linkService.updateLinkDate(link.id());
            var tgChatIds = linkService.getAllTgChatIdByLinkId(link.id());
            sendBotUpdates(link, "Новая информация по вопросу StackOverFlow", tgChatIds);
        }
    }

    private void sendBotUpdates(Link link, String description, List<Long> tgChatIds) {
        botClient.updates(new UpdateRequest(
            link.id(),
            link.url(),
            description,
            tgChatIds
        ));
    }
}

