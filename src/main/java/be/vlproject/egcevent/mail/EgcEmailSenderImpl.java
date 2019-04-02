package be.vlproject.egcevent.mail;

import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EgcEmailSenderImpl implements EgcEmailSender {

    protected static final String TEMPLATE_PATH = "/template";
    protected static final String PAIRING_TEMPLATE_NAME = "pairing.ftl";

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

    private Template pairingTemplate;

    @PostConstruct
    public void postConstruct() throws IOException {
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
        pairingTemplate = freemarkerConfig.getTemplate(PAIRING_TEMPLATE_NAME, "UTF-8");
    }

    @Override
    public void sendPairing(PairingTemplateValues pairingTemplateValues) throws IOException, TemplateException, MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Map<String, Object> model = new HashMap<>();
        model.put("firstName", pairingTemplateValues.getFirstName());
        model.put("lastName", pairingTemplateValues.getLastName());
        model.put("tournamentName", pairingTemplateValues.getTournamentName());
        model.put("roundNumber", pairingTemplateValues.getRoundNumber());
        model.put("opponentFirstName", pairingTemplateValues.getOpponentFirstName());
        model.put("opponentLastName", pairingTemplateValues.getOpponentLastName());
        model.put("opponentLevel", pairingTemplateValues.getOpponentLevel());
        model.put("table", pairingTemplateValues.getTable());
        model.put("color", pairingTemplateValues.getColor());
        model.put("boardSize", pairingTemplateValues.getBoardSize());
        if (StringUtils.hasText(pairingTemplateValues.getDescription())) {
            model.put("description",
                    HtmlUtils.htmlEscape(pairingTemplateValues.getDescription(), "UTF-8")
                            .replaceAll("(?:\\r\\n|\\r|\\n)","<br>"));
        }

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(pairingTemplate, model);

        helper.setTo(pairingTemplateValues.getEmail());
        helper.setText(text, true);
        helper.setSubject(String.format(
                "Pairing: %s round %s",
                pairingTemplateValues.getTournamentName(),
                pairingTemplateValues.getRoundNumber()));

        sender.send(message);
    }
}
