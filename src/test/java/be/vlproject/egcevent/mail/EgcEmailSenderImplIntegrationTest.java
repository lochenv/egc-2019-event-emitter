package be.vlproject.egcevent.mail;

import be.vlproject.egcevent.mail.domain.PairingTemplateValues;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.listeners.MockitoListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EgcEmailSenderImplIntegrationTest {

    @InjectMocks
    private EgcEmailSenderImpl egcEmailSender;

    @Mock
    private JavaMailSender sender;

    @Mock
    private Template pairingTemplate;

    @Mock
    private Template visaInvitationTemplate;

    @Mock
    private Configuration freemarkerConfig;

    @Test
    public void testPostConstruct() throws Exception {
        Mockito.when(freemarkerConfig.getTemplate(EgcEmailSenderImpl.PAIRING_TEMPLATE_NAME, "UTF-8"))
                .thenReturn(pairingTemplate);
        Mockito.when(freemarkerConfig.getTemplate(EgcEmailSenderImpl.VISA_INVITATION_TEMPLATE_NAME, "UTF-8"))
                .thenReturn(visaInvitationTemplate);

        egcEmailSender.postConstruct();
        Mockito.verify(freemarkerConfig).setClassForTemplateLoading(egcEmailSender.getClass(), EgcEmailSenderImpl.TEMPLATE_PATH);

        Assert.assertNotNull(ReflectionTestUtils.getField(egcEmailSender, "pairingTemplate"));
        Assert.assertNotNull(ReflectionTestUtils.getField(egcEmailSender, "visaInvitationTemplate"));
    }

    @Test
    public void testSendEmail() throws Exception {
        ReflectionTestUtils.setField(egcEmailSender, "pairingTemplate", pairingTemplate);
        MimeMessage mockMimeMessage = Mockito.mock(MimeMessage.class);
        ArgumentCaptor<Map<String, Object>> modelCapture = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Address> toCapture = ArgumentCaptor.forClass(Address.class);
        ArgumentCaptor<String> subjectCapture = ArgumentCaptor.forClass(String.class);

        Mockito.when(sender.createMimeMessage()).thenReturn(mockMimeMessage);

        egcEmailSender.sendPairing(new PairingTemplateValues(
                "email", "firstName", "lastName", "opponentFirstName",
                "opponentLastName", "opponentLevel", "table", "color",
                "tournamentName", "boardSize", "roundNumber",
                "this is a \n nice description"
        ));
        Mockito.verify(pairingTemplate).process(modelCapture.capture(), Mockito.isA(Writer.class));
        Mockito.verify(mockMimeMessage).setRecipient(Mockito.eq(MimeMessage.RecipientType.TO), toCapture.capture());
        Mockito.verify(mockMimeMessage).setSubject(subjectCapture.capture());
        Mockito.verify(mockMimeMessage).setContent(Mockito.isA(String.class), Mockito.eq("text/html"));
        Mockito.verify(sender).send(mockMimeMessage);

        // Assertion of Model
        Assert.assertEquals(modelCapture.getValue().get("firstName"), "firstName");
        Assert.assertEquals(modelCapture.getValue().get("lastName"), "lastName");
        Assert.assertEquals(modelCapture.getValue().get("opponentFirstName"), "opponentFirstName");
        Assert.assertEquals(modelCapture.getValue().get("opponentLastName"), "opponentLastName");
        Assert.assertEquals(modelCapture.getValue().get("opponentLevel"), "opponentLevel");
        Assert.assertEquals(modelCapture.getValue().get("table"), "table");
        Assert.assertEquals(modelCapture.getValue().get("color"), "color");
        Assert.assertEquals(modelCapture.getValue().get("tournamentName"), "tournamentName");
        Assert.assertEquals(modelCapture.getValue().get("boardSize"), "boardSize");
        Assert.assertEquals(modelCapture.getValue().get("roundNumber"), "roundNumber");
        Assert.assertEquals(modelCapture.getValue().get("description"), "this is a <br> nice description");

        // Assertion on Message
        Assert.assertEquals(new InternetAddress("email"), toCapture.getValue());
        Assert.assertEquals("Pairing: tournamentName round roundNumber", subjectCapture.getValue());
    }
}