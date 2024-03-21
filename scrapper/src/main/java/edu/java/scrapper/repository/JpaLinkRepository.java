package edu.java.scrapper.repository;

import edu.java.scrapper.models.entity.Link;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    String FIND_OLD_UPDATE_LINKS = """
        SELECT * FROM links
        WHERE EXTRACT(EPOCH FROM (now() - last_check_time)) / 60 > 5
    """;

    Optional<Link> findByUrl(String url);

    @Query(value = FIND_OLD_UPDATE_LINKS, nativeQuery = true)
    List<Link> findOldUpdateLinks();
}
