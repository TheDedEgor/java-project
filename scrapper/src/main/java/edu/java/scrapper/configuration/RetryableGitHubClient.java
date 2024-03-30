package edu.java.scrapper.configuration;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.models.dto.Repository;
import org.springframework.retry.support.RetryTemplate;

public class RetryableGitHubClient implements GitHubClient {

    private final RetryTemplate retryTemplate;
    private final GitHubClient gitHubClient;

    public RetryableGitHubClient(RetryTemplate retryTemplate, GitHubClient gitHubClient) {
        this.retryTemplate = retryTemplate;
        this.gitHubClient = gitHubClient;
    }

    @Override
    public Repository getRepository(String owner, String repo) {
        return retryTemplate.execute((context -> gitHubClient.getRepository(owner, repo)));
    }
}
