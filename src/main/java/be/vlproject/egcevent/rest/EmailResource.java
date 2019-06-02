package be.vlproject.egcevent.rest;

import be.vlproject.egcevent.rest.domain.SimpleEmailRequest;
import be.vlproject.egcevent.rest.domain.SimpleEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/e-mail")
public class EmailResource {

    @Autowired
    private JavaMailSender sender;

    @GetMapping
    public String getEmail() {
        return "Hello World GET";
    }

    @PostMapping
    public SimpleEmailResponse sendEmail(@RequestBody SimpleEmailRequest simpleEmailDto) {

        System.out.println("Sending email");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(simpleEmailDto.getTo());
        message.setText(simpleEmailDto.getText());
        message.setSubject(simpleEmailDto.getSubject());

        message.setFrom("info@egc2019.eu");
        sender.send(message);

        SimpleEmailResponse response = new SimpleEmailResponse();
        response.setFrom("info@egc2019.eu");
        response.setResult("Your email is successfully sent");
        return response;
    }
}
