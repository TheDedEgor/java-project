package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorNettyClientRequestFactory;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {

    @Autowired
    private ApplicationConfig applicationConfig;

    private static final String GIT_HUB_BASE_URL = "https://api.github.com";

    private static final String STACK_OVERFLOW_CLIENT_BASE_URL = "https://api.stackexchange.com";

    private static final String BOT_CLIENT_BASE_URL = "http://localhost:8090";

    @Bean
    public GitHubClient clientService(RetryTemplate retryTemplate) {
        RestClient restClient = RestClient.builder().baseUrl(GIT_HUB_BASE_URL).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        var client = factory.createClient(GitHubClient.class);
        return new RetryableGitHubClient(retryTemplate, client);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(RetryTemplate retryTemplate) {
        RestClient restClient = RestClient.builder()
            .requestFactory(new ReactorNettyClientRequestFactory())
            .baseUrl(STACK_OVERFLOW_CLIENT_BASE_URL)
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        var client = factory.createClient(StackOverflowClient.class);
        return new RetryableStackOverflowClient(retryTemplate, client);
    }

    @Bean
    public BotClient botClient(RetryTemplate retryTemplate) {
        RestClient restClient = RestClient.builder().baseUrl(BOT_CLIENT_BASE_URL).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        var client = factory.createClient(BotClient.class);
        return new RetryableBotClient(retryTemplate, client);
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(getBackOffPolicy());

        CustomRetryPolicy retryPolicy = new CustomRetryPolicy(applicationConfig.retryCodes());
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    private BackOffPolicy getBackOffPolicy() {
        return switch (applicationConfig.backOff()) {
            case CONSTANT -> new FixedBackOffPolicy();
            case LINEAR -> new LinearBackOffPolicy();
            case EXPONENTIAL -> new ExponentialBackOffPolicy();
        };
    }
}
