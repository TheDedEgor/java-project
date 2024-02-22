package edu.java.client;

import edu.java.models.dto.RepositoryDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubClient {
    @GetExchange("/repos/{owner}/{repo}")
    RepositoryDTO getRepository(@PathVariable String owner, @PathVariable String repo);
}
