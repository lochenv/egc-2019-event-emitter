package be.vlproject.egcevent.tournament;

import org.w3c.dom.Document;

public interface MacMahonTournamentParser {

    void parseRound(final Document document, final int roundNumber);
}
