package be.vlproject.egcevent.tournament;

import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class MacMahonTournamentParserImplTest {

    MacMahonTournamentParserImpl parser = new MacMahonTournamentParserImpl();

    @Test
    public void testParsing() throws Exception {
        File testFile = new File("D:\\Dev\\Project\\MacMahon tests\\test tournament.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xml = builder.parse(testFile);

        parser.parseRound(xml, 1);
    }
}