package edu.java.scrapper.scheduler;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.models.dto.UpdateRequest;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
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
    public void update() throws URISyntaxException {
        var links = linkService.findOldUpdateLinks();
        for (var link : links) {
            var url = new URI(link.url());
            var host = url.getHost();
            if (host.equals("github.com")) {
                var path = url.getPath().split("/");
                var owner = path[1];
                var repo = path[2];
                var res = gitHubClient.getRepository(owner, repo);
                if (link.lastCheckTime().isBefore(res.updatedAt())
                    || link.lastCheckTime().isBefore(res.pushedAt())) {
                    linkService.updateLinkDate(link.id());
                    var tgChatIds = linkService.getAllTgChatIdByLinkId(link.id());
                    botClient.updates(new UpdateRequest(
                        link.id(),
                        link.url(),
                        "Репозиторий GitHub обновился",
                        tgChatIds
                    ));
                }
            } else if (host.equals("stackoverflow.com")) {
                var questionId = Long.parseLong(url.getPath().split("/")[2]);
                var res = stackOverflowClient.getQuestion(questionId).questions().getFirst();
                if (link.lastCheckTime().isBefore(res.lastActivityDate())) {
                    linkService.updateLinkDate(link.id());
                    var tgChatIds = linkService.getAllTgChatIdByLinkId(link.id());
                    botClient.updates(new UpdateRequest(
                        link.id(),
                        link.url(),
                        "Новая информация по вопросу StackOverFlow",
                        tgChatIds
                    ));
                }
            }
        }
    }
}

