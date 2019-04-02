package be.vlproject.egcevent.mail;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EgcEmailSender {

    void sendPairing(String to,
                     String player,
                     String tournament,
                     int round,
                     String opponent,
                     int table) throws MessagingException, IOException, TemplateException;
}
