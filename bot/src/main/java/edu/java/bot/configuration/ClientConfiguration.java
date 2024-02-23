package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {
    private static final String SCRAPPER_BASE_URL = "http://localhost:8080";

    @Bean
    public ScrapperClient scrapperClient() {
        RestClient restClient = RestClient.builder().baseUrl(SCRAPPER_BASE_URL).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ScrapperClient.class);
    }
}
