package edu.java.scrapper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.client.ReactorNettyClientRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    private static final String BASE_URL = "http://localhost:8080";

    private static GitHubClient gitHubClient;

    private static StackOverflowClient stackOverflowClient;

    private GitHubClient createGitHubClient() {
        RestClient restClient = RestClient.builder().baseUrl(BASE_URL).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GitHubClient.class);
    }

    private StackOverflowClient createStackOverflowClient() {
        RestClient restClient = RestClient.builder()
            .requestFactory(new ReactorNettyClientRequestFactory())
            .baseUrl(BASE_URL)
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(StackOverflowClient.class);
    }

    @Before
    public void createWebClients() {
        gitHubClient = createGitHubClient();
        stackOverflowClient = createStackOverflowClient();
    }

    @Test
    public void gitHubClientTest() {
        var apiUrl = "/repos/owner/repo";
        var body = "{\"id\": 1, \"name\": \"repo\", \"pushed_at\": \"2011-01-26T19:06:43Z\", \"updated_at\": \"2011-01-26T19:06:43Z\"}";
        stubFor(get(apiUrl)
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody(body)
            )
        );

        var data = gitHubClient.getRepository("owner", "repo");

        assertThat(data.getId()).isEqualTo(1L);
        assertThat(data.getName()).isEqualTo("repo");
        assertThat(data.getUpdatedAt()).isEqualTo("2011-01-26T19:06:43Z");
        assertThat(data.getPushedAt()).isEqualTo("2011-01-26T19:06:43Z");

        verify(getRequestedFor(urlEqualTo(apiUrl)));
    }

    @Test
    public void stackOverflowClientTest() {
        var apiUrl = "/questions/1?order=desc&sort=activity&site=stackoverflow";
        var body = "{\"items\": [{ \"question_id\": 1, \"title\": \"Hello world!\", \"last_activity_date\": 1635163398 }]}";
        stubFor(get(apiUrl)
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody(body)
            )
        );

        var data = stackOverflowClient.getQuestion(1L).getQuestions().getFirst();

        assertThat(data.getId()).isEqualTo(1L);
        assertThat(data.getTitle()).isEqualTo("Hello world!");
        assertThat(data.getLastActivityDate()).isEqualTo("2021-10-25T12:03:18Z");

        verify(getRequestedFor(urlEqualTo(apiUrl)));
    }
}
