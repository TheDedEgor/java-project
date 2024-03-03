package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContainerTest {

    public static PostgreSQLContainer<?> CONTAINER = IntegrationTest.POSTGRES;

    @Test
    public void testContainerStartup() {
        assertThat(CONTAINER.isRunning()).isTrue();
    }
}
