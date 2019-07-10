package be.vlproject.egcevent.tournament;

import org.w3c.dom.Document;

public interface MacMahonTournamentParser {

    void parseAndSend(final Document document) throws Exception;
}
