package edu.java.scrapper.scheduler;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.models.domain.Link;
import edu.java.scrapper.models.dto.UpdateRequest;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.SenderNotification;
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
    private LinkService linkService;

    @Autowired
    private SenderNotification senderNotification;

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
            var updateRequest = createUpdateRequest(link, "Репозиторий GitHub обновился", tgChatIds);
            senderNotification.send(updateRequest);
        }
    }

    private void stackOverflowHandler(Link link, URI url) {
        var questionId = Long.parseLong(url.getPath().split("/")[2]);
        var res = stackOverflowClient.getQuestion(questionId).questions().getFirst();
        if (link.lastCheckTime().isBefore(res.lastActivityDate())) {
            linkService.updateLinkDate(link.id());
            var tgChatIds = linkService.getAllTgChatIdByLinkId(link.id());
            var updateRequest = createUpdateRequest(link, "Новая информация по вопросу StackOverFlow", tgChatIds);
            senderNotification.send(updateRequest);
        }
    }

    private UpdateRequest createUpdateRequest(Link link, String description, List<Long> tgChatIds) {
        return new UpdateRequest(
            link.id(),
            link.url(),
            description,
            tgChatIds
        );
    }
}

