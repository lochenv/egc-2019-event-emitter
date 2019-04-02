package be.vlproject.egcevent.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EgcEmailSenderImpl implements EgcEmailSender {

    private static final String TEMPLATE_PATH = "/template";
    private static final String PAIRING_TEMPLATE_NAME = "pairing.ftl";

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

    private Template pairingTemplate;

    @PostConstruct
    public void postConstruct() throws IOException {
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
        pairingTemplate = freemarkerConfig.getTemplate(PAIRING_TEMPLATE_NAME);
    }

    @Override
    public void sendPairing(
            final String to,
            final String player,
            final String tournament,
            final int round,
            final String opponent,
            final int table) throws MessagingException, TemplateException, IOException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Map<String, Object> model = new HashMap<>();
        model.put("player", player);
        model.put("tournament", tournament);
        model.put("round", round);
        model.put("opponent", opponent);
        model.put("table", table);

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(pairingTemplate, model);

        helper.setTo(to);
        helper.setText(text, true);
        helper.setSubject(String.format("Pairing of %s round %d", tournament, round));

        sender.send(message);
    }
}
