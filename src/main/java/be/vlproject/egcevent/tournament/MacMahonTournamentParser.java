package be.vlproject.egcevent.tournament;

import freemarker.template.TemplateException;
import org.w3c.dom.Document;

import javax.mail.MessagingException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public interface MacMahonTournamentParser {

    void parseAndSend(final Document document) throws XPathExpressionException, TemplateException, IOException, MessagingException;
}
