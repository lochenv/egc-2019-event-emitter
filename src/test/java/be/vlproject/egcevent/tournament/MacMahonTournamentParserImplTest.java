package be.vlproject.egcevent.tournament;

import be.vlproject.egcevent.mail.EgcEmailSender;
import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import be.vlproject.egcevent.services.PlayerHttpService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class MacMahonTournamentParserImplTest {

    @InjectMocks
    private MacMahonTournamentParserImpl parser;

    @Mock
    private EgcEmailSender emailSender;

    @Mock
    private PlayerHttpService playerHttpService;

    @Captor
    private ArgumentCaptor<PairingTemplateValues> pairingCaptor;

    @Test
    public void testParsingFirstRound() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xml = builder.parse(getClass().getClassLoader().getResourceAsStream(
                "test-tournament.xml"
        ));

        Mockito.when(playerHttpService.findAll()).thenReturn(Collections.emptyList());
        parser.parseAndSend(xml);
        Mockito.verify(playerHttpService).findAll();
        Mockito.verify(emailSender, Mockito.times(2)).sendPairing(pairingCaptor.capture());

        Assert.assertEquals("Wrong number of event sent", 2, pairingCaptor.getAllValues().size());
        if (pairingCaptor.getAllValues().size() == 2) {
            PairingTemplateValues firstPairing = pairingCaptor.getAllValues().get(0);
            Assert.assertEquals("Wrong email for first pairing", "email1@provider.com", firstPairing.getEmail());
            Assert.assertEquals("Wrong firstName for first pairing", "PlayerOne", firstPairing.getFirstName());
            Assert.assertEquals("Wrong lastName for first pairing", "One", firstPairing.getLastName());
            Assert.assertEquals("Wrong Opponent firstName for first pairing", "PlayerThree", firstPairing.getOpponentFirstName());
            Assert.assertEquals("Wrong Opponent lastName for first pairing", "Three", firstPairing.getOpponentLastName());
            Assert.assertEquals("Wrong Opponent level for first pairing", "5d", firstPairing.getOpponentLevel());
            Assert.assertEquals("Wrong table for first pairing", "1", firstPairing.getTable());
            Assert.assertEquals("Wrong color for first pairing", "Black", firstPairing.getColor());
            Assert.assertEquals("Wrong tournament name for first pairing", "EGC test tournament", firstPairing.getTournamentName());
            Assert.assertEquals("Wrong board size for first pairing", "19", firstPairing.getBoardSize());
            Assert.assertEquals("Wrong round number for first pairing", "1", firstPairing.getRoundNumber());
            Assert.assertEquals("Wrong tournament description for first pairing",
                    "Time settings: blablabla\n" +
                            "Komi : X.5\n" +
                            "Tables 1-10 are in room A\n" +
                            "Tables 11-40 are in room B\n" +
                            "Tables 41-50 are in room C\n" +
                            "Table 51-60 are in room D",
                    firstPairing.getDescription());

            PairingTemplateValues secondPairing = pairingCaptor.getAllValues().get(1);
            Assert.assertEquals("Wrong email for second pairing", "email2@provider.com", secondPairing.getEmail());
            Assert.assertEquals("Wrong firstName for second pairing", "PlayerTwo", secondPairing.getFirstName());
            Assert.assertEquals("Wrong lastName for second pairing", "Two", secondPairing.getLastName());
            Assert.assertEquals("Wrong Opponent firstName for second pairing", "PlayerFour", secondPairing.getOpponentFirstName());
            Assert.assertEquals("Wrong Opponent lastName for second pairing", "Four", secondPairing.getOpponentLastName());
            Assert.assertEquals("Wrong Opponent level for second pairing", "5k", secondPairing.getOpponentLevel());
            Assert.assertEquals("Wrong table for second pairing", "2", secondPairing.getTable());
            Assert.assertEquals("Wrong color for second pairing", "White", secondPairing.getColor());
            Assert.assertEquals("Wrong tournament name for second pairing", "EGC test tournament", secondPairing.getTournamentName());
            Assert.assertEquals("Wrong board size for second pairing", "19", secondPairing.getBoardSize());
            Assert.assertEquals("Wrong round number for second pairing", "1", secondPairing.getRoundNumber());
            Assert.assertEquals("Wrong tournament description for second pairing",
                    "Time settings: blablabla\n" +
                            "Komi : X.5\n" +
                            "Tables 1-10 are in room A\n" +
                            "Tables 11-40 are in room B\n" +
                            "Tables 41-50 are in room C\n" +
                            "Table 51-60 are in room D",
                    firstPairing.getDescription());
        }
    }

    @Test
    public void testParsingSecondRound() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xml = builder.parse(getClass().getClassLoader().getResourceAsStream(
                "test-tournament-2.xml"
        ));

        parser.parseAndSend(xml);
        Mockito.verify(emailSender, Mockito.times(2)).sendPairing(pairingCaptor.capture());

        Assert.assertEquals("Wrong number of event sent", 2, pairingCaptor.getAllValues().size());
        if (pairingCaptor.getAllValues().size() == 2) {
            PairingTemplateValues firstPairing = pairingCaptor.getAllValues().get(0);
            Assert.assertEquals("Wrong email for first pairing", "email2@provider.com", firstPairing.getEmail());
            Assert.assertEquals("Wrong firstName for first pairing", "PlayerTwo", firstPairing.getFirstName());
            Assert.assertEquals("Wrong lastName for first pairing", "Two", firstPairing.getLastName());
            Assert.assertEquals("Wrong Opponent firstName for first pairing", "PlayerThree", firstPairing.getOpponentFirstName());
            Assert.assertEquals("Wrong Opponent lastName for first pairing", "Three", firstPairing.getOpponentLastName());
            Assert.assertEquals("Wrong Opponent level for first pairing", "5d", firstPairing.getOpponentLevel());
            Assert.assertEquals("Wrong table for first pairing", "1", firstPairing.getTable());
            Assert.assertEquals("Wrong color for first pairing", "Black", firstPairing.getColor());
            Assert.assertEquals("Wrong tournament name for first pairing", "EGC test tournament", firstPairing.getTournamentName());
            Assert.assertEquals("Wrong board size for first pairing", "19", firstPairing.getBoardSize());
            Assert.assertEquals("Wrong round number for first pairing", "2", firstPairing.getRoundNumber());
            Assert.assertEquals("Wrong tournament description for first pairing",
                    "Time settings: blablabla\n" +
                            "Komi : X.5\n" +
                            "Tables 1-10 are in room A\n" +
                            "Tables 11-40 are in room B\n" +
                            "Tables 41-50 are in room C\n" +
                            "Table 51-60 are in room D",
                    firstPairing.getDescription());

            PairingTemplateValues secondPairing = pairingCaptor.getAllValues().get(1);
            Assert.assertEquals("Wrong email for second pairing", "email1@provider.com", secondPairing.getEmail());
            Assert.assertEquals("Wrong firstName for second pairing", "PlayerOne", secondPairing.getFirstName());
            Assert.assertEquals("Wrong lastName for second pairing", "One", secondPairing.getLastName());
            Assert.assertEquals("Wrong Opponent firstName for second pairing", "PlayerFour", secondPairing.getOpponentFirstName());
            Assert.assertEquals("Wrong Opponent lastName for second pairing", "Four", secondPairing.getOpponentLastName());
            Assert.assertEquals("Wrong Opponent level for second pairing", "5k", secondPairing.getOpponentLevel());
            Assert.assertEquals("Wrong table for second pairing", "2", secondPairing.getTable());
            Assert.assertEquals("Wrong color for second pairing", "White", secondPairing.getColor());
            Assert.assertEquals("Wrong tournament name for second pairing", "EGC test tournament", secondPairing.getTournamentName());
            Assert.assertEquals("Wrong board size for second pairing", "19", secondPairing.getBoardSize());
            Assert.assertEquals("Wrong round number for second pairing", "2", secondPairing.getRoundNumber());
            Assert.assertEquals("Wrong tournament description for second pairing",
                    "Time settings: blablabla\n" +
                            "Komi : X.5\n" +
                            "Tables 1-10 are in room A\n" +
                            "Tables 11-40 are in room B\n" +
                            "Tables 41-50 are in room C\n" +
                            "Table 51-60 are in room D",
                    firstPairing.getDescription());
        }
    }
}
