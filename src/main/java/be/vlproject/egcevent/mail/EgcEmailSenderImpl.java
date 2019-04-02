package be.vlproject.egcevent.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EgcEmailSenderImpl implements EgcEmailSender {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void sendPairing(
            final String to,
            final String player,
            final String tournament,
            final int round,
            final String opponent,
            final int table) throws MessagingException, IOException, TemplateException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Map<String, Object> model = new HashMap<>();
        model.put("player", player);
        model.put("tournament", tournament);
        model.put("round", round);
        model.put("opponent", opponent);
        model.put("table", table);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
        Template template = freemarkerConfig.getTemplate("pairing.ftl");

        String text =
                FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setTo(to);
        helper.setText(text, true);
        helper.setSubject(String.format("Pairing of %s round %d", tournament, round));

        sender.send(message);
    }
}
