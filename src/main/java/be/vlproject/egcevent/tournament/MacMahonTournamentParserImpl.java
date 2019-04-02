package be.vlproject.egcevent.tournament;

import be.vlproject.egcevent.tournament.domain.GoPlayer;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class MacMahonTournamentParserImpl {

    private static final String PLAYERS_NODE = "/Tournament/IndividualParticipant";
    private static final String GOPLAYER_SUBNODE = "GoPlayer";
    private static final String GOPLAYER_FIRST_NAME = "FirstName";
    private static final String GOPLAYER_LAST_NAME = "Surname";
    private static final String GOPLAYER_LEVEL = "GoLevel";

    private static final String TOURNAMENT_ROUND_NODE = "/Tournament/TournamentRound[RoundNumber = '%d']";
    private static final String BLACK_PLAYER_ID = "Black";
    private static final String WHITE_PLAYER_ID = "White";
    private static final String BOARD_NUMBER = "BoardNumber";

    public void parseRound(final Document document, final int roundNumber) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();

        Map<String, GoPlayer> registeredPlayers = extractPlayerList(document);

        XPath roundSelector = factory.newXPath();
        Node round = (Node) roundSelector.evaluate(String.format(TOURNAMENT_ROUND_NODE, roundNumber), document, XPathConstants.NODE);

        NodeList parings = (NodeList) roundSelector.evaluate("Pairing", round, XPathConstants.NODESET);

        for (int i=0; i<parings.getLength(); i++) {

            System.out.println(String.format("Black player : %s, white player : %s, table %s",
                    registeredPlayers.get(roundSelector.evaluate(BLACK_PLAYER_ID, parings.item(i))).getLastName(),
                    registeredPlayers.get(roundSelector.evaluate(WHITE_PLAYER_ID, parings.item(i))).getLastName(),
                    roundSelector.evaluate(BOARD_NUMBER, parings.item(i))
                    ));
        }
    }

    private Map<String, GoPlayer> extractPlayerList(final Document document) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();

        XPath players = factory.newXPath();
        NodeList playersNode = (NodeList) players.evaluate(PLAYERS_NODE, document, XPathConstants.NODESET);

        Map<String, GoPlayer> registeredPlayers = new HashMap<>();

        for (int i=0; i<playersNode.getLength(); i++) {
            Node playerNode = (Node) players.evaluate(GOPLAYER_SUBNODE, playersNode.item(i), XPathConstants.NODE);
            registeredPlayers.put(
                    players.evaluate("Id", playersNode.item(i)),
                    new GoPlayer(
                            players.evaluate(GOPLAYER_LAST_NAME, playerNode),
                            players.evaluate(GOPLAYER_FIRST_NAME, playerNode),
                            players.evaluate(GOPLAYER_LEVEL, playerNode)
                    ));
        }

        return registeredPlayers;
    }
}
