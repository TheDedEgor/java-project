package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.models.domain.Link;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("MultipleStringLiterals")
public class JdbcLinkService implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        try {
            var optionalChatId = linkRepository.getChatIdByTgChatId(tgChatId);
            if (optionalChatId.isPresent()) {
                var chatId = optionalChatId.get();
                return linkRepository.add(chatId, url.toString());
            } else {
                throw new NotFoundChatException("Not found chat");
            }
        } catch (DuplicateKeyException ex) {
            throw new ExistLinkException("The link already exists");
        }
    }

    @Override
    public Link remove(Long tgChatId, URI url) throws NotFoundLinkException, NotFoundChatException {
        var optionalChatId = linkRepository.getChatIdByTgChatId(tgChatId);
        if (optionalChatId.isEmpty()) {
            throw new NotFoundChatException("Not found chat");
        }
        var optionalLinkId = linkRepository.getLinkIdByUrl(url.toString());
        if (optionalLinkId.isEmpty()) {
            throw new NotFoundLinkException("Not found link");
        }
        var chatId = optionalChatId.get();
        var linkId = optionalLinkId.get();
        return linkRepository.remove(chatId, linkId);
    }

    @Override
    public List<Long> getAllTgChatIdByLinkId(Long linkId) {
        return linkRepository.getAllTgChatIdByLinkId(linkId);
    }

    @Override
    public void updateLinkDate(Long linkId) {
        linkRepository.updateLinkDate(linkId);
    }

    @Override
    public Collection<Link> listAll(Long tgChatId) throws NotFoundChatException {
        var optionalChatId = linkRepository.getChatIdByTgChatId(tgChatId);
        if (optionalChatId.isEmpty()) {
            throw new NotFoundChatException("Not found chat");
        }
        var chatId = optionalChatId.get();
        return linkRepository.findAll(chatId);
    }

    @Override
    public Collection<Link> findOldUpdateLinks() {
        return linkRepository.findOldUpdateLinks();
    }
}
