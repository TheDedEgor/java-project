package edu.java.scrapper.repository;

import edu.java.scrapper.models.domain.Link;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
public class JdbcLinkRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertIntoLink;

    @Autowired
    public JdbcLinkRepository(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
        insertIntoLink = new SimpleJdbcInsert(jdbcTemplate).withTableName("links").usingGeneratedKeyColumns("id")
            .usingColumns("url");
    }

    public List<Link> findAll(Long chatId) {
        return jdbcTemplate.query(
            """
                SELECT
                    links.id,
                    links.url,
                    links.last_check_time
                FROM links
                    JOIN chats_links cl ON links.id = cl.link_id
                    JOIN chats ch ON ch.id = cl.chat_id
                WHERE ch.id = ?
                """,
            (row, item) ->
                new Link(
                    row.getLong("id"),
                    row.getString("url"),
                    parseDate(row.getString("last_check_time"))
                ), chatId
        );
    }

    public List<Link> findOldUpdateLinks() {
        return jdbcTemplate.query(
            """
                SELECT * FROM links
                WHERE EXTRACT(EPOCH FROM (now() - last_check_time)) / 60 > 5;
                """,
            (row, item) -> new Link(
                row.getLong("id"),
                row.getString("url"),
                parseDate(row.getString("last_check_time"))
            )
        );
    }

    public List<Long> getAllTgChatIdByLinkId(Long linkId) {
        return jdbcTemplate.query(
            """
                SELECT
                    ch.tg_chat_id
                FROM links
                    JOIN chats_links cl ON links.id = cl.link_id
                    JOIN chats ch ON ch.id = cl.chat_id
                WHERE links.id = ?
                """,
            (row, item) ->
                row.getLong("tg_chat_id"), linkId
        );
    }

    @Transactional
    public void updateLinkDate(Long linkId) {
        jdbcTemplate.update("UPDATE links SET last_check_time = now() WHERE id = ?", linkId);
    }

    @Transactional
    public Link add(Long chatId, String url) {
        var optionalLinkId = getLinkIdByUrl(url);
        Long linkId;
        if (optionalLinkId.isEmpty()) {
            final var parameters = Map.of("url", url);
            linkId = (Long) insertIntoLink.executeAndReturnKey(parameters);
        } else {
            linkId = optionalLinkId.get();
        }

        jdbcTemplate.update("INSERT INTO chats_links(chat_id, link_id) VALUES (?, ?)", chatId, linkId);

        return getLinkById(linkId).get();
    }

    @Transactional
    public Link remove(Long chatId, Long linkId) {
        var link = getLinkById(linkId).get();
        jdbcTemplate.update("DELETE FROM chats_links WHERE chat_id = ? AND link_id = ? ", chatId, linkId);
        var ids = jdbcTemplate.query("SELECT id FROM chats_links WHERE link_id = ? LIMIT 1",
            (row, item) -> row.getLong("id"), linkId
        );
        if (ids.isEmpty()) {
            jdbcTemplate.update("DELETE FROM links WHERE id = ? ", linkId);
        }
        return link;
    }

    public Optional<Long> getChatIdByTgChatId(Long tgChatId) {
        var ids = jdbcTemplate.query("SELECT id FROM chats WHERE tg_chat_id = ?", (row, item) ->
            row.getLong("id"), tgChatId);
        if (ids.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ids.getFirst());
    }

    public Optional<Long> getLinkIdByUrl(String url) {
        var ids = jdbcTemplate.query("SELECT * FROM links WHERE url = ?",
            (row, item) -> row.getLong("id"), url
        );
        if (ids.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ids.getFirst());
    }

    private Optional<Link> getLinkById(Long linkId) {
        var links = jdbcTemplate.query("SELECT * FROM links WHERE id = ?",
            (row, item) -> new Link(
                row.getLong("id"),
                row.getString("url"),
                parseDate(row.getString("last_check_time"))
            ), linkId
        );
        if (links.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(links.getFirst());
    }

    private static OffsetDateTime parseDate(String date) {
        if (date == null) {
            return null;
        }
        var formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss.")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, false)
            .appendPattern("x")
            .toFormatter();

        return OffsetDateTime.parse(date, formatter);
    }
}
