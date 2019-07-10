package be.vlproject.egcevent.mail;

import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import be.vlproject.egcevent.mail.domain.VisaInvitationTemplateValues;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EgcEmailSenderImpl implements EgcEmailSender {

    protected static final String TEMPLATE_PATH = "/template";
    protected static final String PAIRING_TEMPLATE_NAME = "pairing.ftl";
    protected static final String VISA_INVITATION_TEMPLATE_NAME = "visa-invitation.ftl";
    protected static final String VISA_INVITATION_EMAIL_TEMPLATE_NAME = "visa-invitation-email.ftl";

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

    private Template pairingTemplate;
    private Template visaInvitationTemplate;
    private Template visaInvitationEmailTemplate;

    @PostConstruct
    public void postConstruct() throws IOException {
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
        pairingTemplate = freemarkerConfig.getTemplate(PAIRING_TEMPLATE_NAME, "UTF-8");
        visaInvitationTemplate = freemarkerConfig.getTemplate(VISA_INVITATION_TEMPLATE_NAME, "UTF-8");
        visaInvitationEmailTemplate = freemarkerConfig.getTemplate(VISA_INVITATION_EMAIL_TEMPLATE_NAME, "UTF-8");
    }

    @Override
    public void sendPairing(PairingTemplateValues pairingTemplateValues) throws IOException, TemplateException, MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(pairingTemplate, pairingTemplateValues.toMap());

        helper.setFrom("info@egc2019.eu");
        helper.setTo(pairingTemplateValues.getEmail());
        helper.setText(text, true);
        helper.setSubject(String.format(
                "Pairing: %s round %s",
                pairingTemplateValues.getTournamentName(),
                pairingTemplateValues.getRoundNumber()));

        sender.send(message);
    }

    @Override
    public void sendVisaInvitation(VisaInvitationTemplateValues visaInvitationTemplateValues) throws IOException, TemplateException, MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String pdfText = FreeMarkerTemplateUtils.processTemplateIntoString(visaInvitationTemplate, visaInvitationTemplateValues.toMap());
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("playerName", visaInvitationTemplateValues.getPlayerName());
        String emailText = FreeMarkerTemplateUtils.processTemplateIntoString(visaInvitationEmailTemplate, emailMap);

        helper.setFrom("visa@egc2019.eu");
        helper.setCc("visa@egc2019.eu");
        helper.setTo(visaInvitationTemplateValues.getEmail());
        helper.setText(emailText, true);
        helper.setSubject(String.format(
                "Visa invitation to EGC for : %s",
                visaInvitationTemplateValues.getPlayerName()));

        String fileName = "c:\\temp\\visa-invitation-" + visaInvitationTemplateValues.getPlayerName() + ".pdf";
        PdfConverterExtension.exportToPdf(
                new FileOutputStream(fileName),
                pdfText,
                "",
                BaseRendererBuilder.TextDirection.LTR
        );
        FileSystemResource file = new FileSystemResource(fileName);
        helper.addAttachment(file.getFilename(), file);

        sender.send(message);
    }
}
