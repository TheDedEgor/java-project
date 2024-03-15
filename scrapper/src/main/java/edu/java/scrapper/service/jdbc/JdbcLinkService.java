package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.models.entity.Link;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        try {
            return linkRepository.add(tgChatId, url.toString());
        } catch (DuplicateKeyException ex) {
            throw new ExistLinkException("The link already exists");
        }
    }

    @Override
    public Link remove(Long tgChatId, URI url) throws NotFoundLinkException, NotFoundChatException {
        return linkRepository.remove(tgChatId, url.toString());
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
    public Collection<Link> listAll(Long tgChatId) {
        return linkRepository.findAll(tgChatId);
    }

    @Override
    public Collection<Link> findOldUpdateLinks() {
        return linkRepository.findOldUpdateLinks();
    }
}
