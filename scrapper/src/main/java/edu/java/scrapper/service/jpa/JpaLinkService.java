package edu.java.scrapper.service.jpa;

import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.models.domain.Link;
import edu.java.scrapper.models.entity.Chat;
import edu.java.scrapper.repository.JpaChatRepository;
import edu.java.scrapper.repository.JpaLinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("MultipleStringLiterals")
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;

    private final JpaChatRepository chatRepository;

    public JpaLinkService(JpaLinkRepository linkRepository, JpaChatRepository chatRepository) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    @Transactional
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        try {
            var optionalChat = chatRepository.findByTgChatId(tgChatId);
            if (optionalChat.isPresent()) {
                var chat = optionalChat.get();
                var optionalLink = linkRepository.findByUrl(url.toString());
                edu.java.scrapper.models.entity.Link link;
                if (optionalLink.isEmpty()) {
                    link = new edu.java.scrapper.models.entity.Link(url.toString(), chat);
                } else {
                    link = optionalLink.get();
                    link.getChats().add(chat);
                    chat.getLinks().add(link);
                }
                link = linkRepository.save(link);
                return new Link(link.getId(), link.getUrl(), link.getLastCheckTime());
            } else {
                throw new NotFoundChatException("Not found chat");
            }
        } catch (DuplicateKeyException ex) {
            throw new ExistLinkException("The link already exists");
        }
    }

    @Override
    @Transactional
    public Link remove(Long tgChatId, URI url) throws NotFoundLinkException, NotFoundChatException {
        var optionalChat = chatRepository.findByTgChatId(tgChatId);
        if (optionalChat.isEmpty()) {
            throw new NotFoundChatException("Not found chat");
        }
        var optionalLink = linkRepository.findByUrl(url.toString());
        if (optionalLink.isEmpty()) {
            throw new NotFoundLinkException("Not found link");
        }
        var chat = optionalChat.get();
        var link = optionalLink.get();
        chat.getLinks().remove(link);
        chatRepository.save(chat);
        return new Link(link.getId(), link.getUrl(), link.getLastCheckTime());
    }

    @Override
    @Transactional
    public List<Long> getAllTgChatIdByLinkId(Long linkId) {
        var link = linkRepository.findById(linkId).get();
        return link.getChats().stream()
            .map(Chat::getTgChatId)
            .toList();
    }

    @Override
    @Transactional
    public void updateLinkDate(Long linkId) {
        var link = linkRepository.findById(linkId).get();
        link.setLastCheckTime(OffsetDateTime.now());
        linkRepository.save(link);
    }

    @Override
    @Transactional
    public Collection<Link> listAll(Long tgChatId) throws NotFoundChatException {
        var optionalChat = chatRepository.findByTgChatId(tgChatId);
        if (optionalChat.isPresent()) {
            var chat = optionalChat.get();
            return chat.getLinks().stream()
                .map(link -> new Link(link.getId(), link.getUrl(), link.getLastCheckTime()))
                .toList();
        } else {
            throw new NotFoundChatException("Not found chat");
        }
    }

    @Override
    @Transactional
    public Collection<Link> findOldUpdateLinks() {
        return linkRepository.findOldUpdateLinks().stream()
            .map(link -> new Link(link.getId(), link.getUrl(), link.getLastCheckTime()))
            .toList();
    }
}
