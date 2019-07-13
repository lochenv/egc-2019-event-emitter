package be.vlproject.egcevent.tournament;

import be.vlproject.egcevent.mail.EgcEmailSender;
import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import be.vlproject.egcevent.services.PlayerHttpService;
import be.vlproject.egcevent.tournament.domain.EgcPlayer;
import be.vlproject.egcevent.tournament.domain.GoPlayer;
import be.vlproject.egcevent.tournament.domain.SendPairingReport;
import be.vlproject.egcevent.tournament.domain.TournamentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.*;

@Component
public class MacMahonTournamentParserImpl implements MacMahonTournamentParser {

    private static final String PLAYERS_NODE = "/Tournament/IndividualParticipant";
    private static final String GOPLAYER_SUBNODE = "GoPlayer";
    private static final String GOPLAYER_FIRST_NAME = "FirstName";
    private static final String GOPLAYER_LAST_NAME = "Surname";
    private static final String GOPLAYER_EMAIL = "Comment";
    private static final String GOPLAYER_LEVEL = "GoLevel";

    private static final String TOURNAMENT_ROUND_NODE = "/Tournament/TournamentRound[RoundNumber = '%d']";
    private static final String BLACK_PLAYER_ID = "Black";
    private static final String WHITE_PLAYER_ID = "White";
    private static final String BOARD_NUMBER = "BoardNumber";

    @Autowired
    private EgcEmailSender egcEmailSender;

    @Autowired
    private PlayerHttpService egcPlayerService;

    public SendPairingReport parseAndSend(final Document document) throws Exception {
        XPathFactory factory = XPathFactory.newInstance();

        // Extract informations from the file
        TournamentInfo tournamentInfo = extractTournamentInfo(document);
        Map<String, GoPlayer> registeredPlayers = extractPlayerList(document);

        XPath roundSelector = factory.newXPath();
        Node round = (Node) roundSelector.evaluate(String.format(TOURNAMENT_ROUND_NODE, tournamentInfo.getCurrentRound()), document, XPathConstants.NODE);

        NodeList parings = (NodeList) roundSelector.evaluate("Pairing", round, XPathConstants.NODESET);

        SendPairingReport report = new SendPairingReport();
        for (int i = 0; i < parings.getLength(); i++) {
            GoPlayer black = registeredPlayers.get(roundSelector.evaluate(BLACK_PLAYER_ID, parings.item(i)));
            GoPlayer white = registeredPlayers.get(roundSelector.evaluate(WHITE_PLAYER_ID, parings.item(i)));
            if (StringUtils.hasText(black.getEmail())) {
                try {
                    egcEmailSender.sendPairing(
                            generateTemplateValues(
                                    tournamentInfo,
                                    black,
                                    white,
                                    roundSelector.evaluate(BOARD_NUMBER, parings.item(i)), true));
                    report.addSucceeded(black);
                } catch (Exception e) {
                    e.printStackTrace();
                    report.addInError(black);
                }
            }

            if (StringUtils.hasText(white.getEmail())) {
                try {
                    egcEmailSender.sendPairing(
                            generateTemplateValues(
                                    tournamentInfo,
                                    white,
                                    black,
                                    roundSelector.evaluate(BOARD_NUMBER, parings.item(i)), false));
                    report.addSucceeded(white);
                } catch (Exception e) {
                    e.printStackTrace();
                    report.addInError(white);
                }
            }
        }

        return report;
    }

    private PairingTemplateValues generateTemplateValues(
            final TournamentInfo tournamentInfo,
            final GoPlayer player,
            final GoPlayer opponent,
            final String table,
            final Boolean isBlack) {
        return new PairingTemplateValues(
                player.getEmail(),
                player.getFirstName(),
                player.getLastName(),
                opponent.getFirstName(),
                opponent.getLastName(),
                opponent.getLevel(),
                table,
                isBlack ? "Black" : "White",
                tournamentInfo.getName(),
                tournamentInfo.getBoardSize(),
                tournamentInfo.getCurrentRound().toString(),
                tournamentInfo.getDescription()
        );
    }

    private TournamentInfo extractTournamentInfo(final Document document) throws Exception {
        XPathFactory factory = XPathFactory.newInstance();
        XPath tournamentInfoSelector = factory.newXPath();

        return new TournamentInfo(
                tournamentInfoSelector.evaluate("/Tournament/Name", document),
                tournamentInfoSelector.evaluate("/Tournament/Boardsize", document),
                tournamentInfoSelector.evaluate("/Tournament/Description", document),
                ((Double) tournamentInfoSelector.evaluate("/Tournament/CurrentRoundNumber", document, XPathConstants.NUMBER)).intValue()
        );

    }

    private Map<String, GoPlayer> extractPlayerList(final Document document) throws Exception {
        XPathFactory factory = XPathFactory.newInstance();
        XPath players = factory.newXPath();

        NodeList playersNode = (NodeList) players.evaluate(PLAYERS_NODE, document, XPathConstants.NODESET);
        Map<String, GoPlayer> registeredPlayers = new HashMap<>();

        // Retrieve all players from Database
        List<EgcPlayer> egcPlayers = egcPlayerService.findAll();

        for (int i = 0; i < playersNode.getLength(); i++) {
            Node playerNode = (Node) players.evaluate(GOPLAYER_SUBNODE, playersNode.item(i), XPathConstants.NODE);
            GoPlayer extracted = new GoPlayer(
                    players.evaluate(GOPLAYER_LAST_NAME, playerNode),
                    players.evaluate(GOPLAYER_FIRST_NAME, playerNode),
                    players.evaluate(GOPLAYER_EMAIL, playersNode.item(i)),
                    players.evaluate(GOPLAYER_LEVEL, playerNode)
            );
            if (!StringUtils.hasText(extracted.getEmail())) {
                egcPlayers.stream()
                        .filter(withLastName -> withLastName.getLastName().equals(extracted.getLastName()))
                        .filter(withFirstName -> withFirstName.getFirstName().equals(extracted.getFirstName()))
                        .filter(EgcPlayer::getNotification)
                        .findAny()
                        .ifPresent(egcPlayer -> extracted.setEmail(egcPlayer.getEmail()));
            }
            registeredPlayers.put(
                    players.evaluate("Id", playersNode.item(i)),
                    extracted);
        }

        return registeredPlayers;
    }
}
