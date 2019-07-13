package be.vlproject.egcevent.services;

import be.vlproject.egcevent.tournament.domain.EgcPlayer;

import java.io.IOException;
import java.util.List;

/**
 * Interface to reach our players list
 */
public interface PlayerHttpService {

    List<EgcPlayer> findAll() throws IOException;
}
