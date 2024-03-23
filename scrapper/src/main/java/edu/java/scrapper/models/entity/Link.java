package edu.java.scrapper.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "links",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "url")
    }
)
@Getter @Setter @NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private OffsetDateTime lastCheckTime = OffsetDateTime.now();

    @ManyToMany(mappedBy = "links")
    private List<Chat> chats = new ArrayList<>();

    public Link(String url, Chat chat) {
        this.url = url;
        this.chats.add(chat);
        chat.getLinks().add(this);
    }
}
