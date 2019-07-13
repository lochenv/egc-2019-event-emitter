package be.vlproject.egcevent.tournament;

import be.vlproject.egcevent.tournament.domain.SendPairingReport;
import org.w3c.dom.Document;

public interface MacMahonTournamentParser {

    SendPairingReport parseAndSend(final Document document) throws Exception;
}
