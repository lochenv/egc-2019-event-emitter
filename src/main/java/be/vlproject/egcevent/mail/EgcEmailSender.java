package be.vlproject.egcevent.mail;

import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EgcEmailSender {

    void sendPairing(PairingTemplateValues pairingTemplateValues) throws IOException, TemplateException, MessagingException;
}
