package edu.java.scrapper.service;

import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.models.entity.Link;
import java.net.URI;
import java.util.Collection;
import java.util.List;

public interface LinkService {
    Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException;

    Link remove(Long tgChatId, URI url) throws NotFoundLinkException, NotFoundChatException;

    List<Long> getAllTgChatIdByLinkId(Long linkId);

    void updateLinkDate(Long linkId);

    Collection<Link> listAll(Long tgChatId);

    Collection<Link> findOldUpdateLinks();
}
