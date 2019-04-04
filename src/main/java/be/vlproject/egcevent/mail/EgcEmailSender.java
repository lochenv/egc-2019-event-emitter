package be.vlproject.egcevent.mail;

import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import be.vlproject.egcevent.mail.domain.VisaInvitationTemplateValues;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EgcEmailSender {

    void sendPairing(PairingTemplateValues pairingTemplateValues) throws IOException, TemplateException, MessagingException;

    void sendVisaInvitation(VisaInvitationTemplateValues visaInvitationTemplateValues) throws IOException, TemplateException, MessagingException;
}
