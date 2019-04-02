package be.vlproject.egcevent.mail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EgcEmailSenderImplIntegrationTest {

    @Autowired
    private EgcEmailSender egcEmailSender;

    @Test
    public void contextLoads() {
        assertNotNull(egcEmailSender);
    }

    @Test
    @Ignore("Disable due to default values for UserName and Password")
    public void testSendEmail() throws Exception {
        egcEmailSender.sendPairing(
                "your-email@gmail.com",
                "player name",
                "Test tournament",
                1,
                "opponent name",
                1
        );
    }
}