package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorNettyClientRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {

    private static final String GIT_HUB_BASE_URL = "https://api.github.com";

    private static final String STACK_OVERFLOW_CLIENT_BASE_URL = "https://api.stackexchange.com";

    private static final String BOT_CLIENT_BASE_URL = "http://localhost:8090";

    @Bean
    public GitHubClient clientService() {
        RestClient restClient = RestClient.builder().baseUrl(GIT_HUB_BASE_URL).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(GitHubClient.class);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        RestClient restClient = RestClient.builder()
            .requestFactory(new ReactorNettyClientRequestFactory())
            .baseUrl(STACK_OVERFLOW_CLIENT_BASE_URL)
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(StackOverflowClient.class);
    }

    @Bean
    public BotClient botClient() {
        RestClient restClient = RestClient.builder().baseUrl(BOT_CLIENT_BASE_URL).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(BotClient.class);
    }
}
