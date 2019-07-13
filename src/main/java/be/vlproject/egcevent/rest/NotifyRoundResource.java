package be.vlproject.egcevent.rest;

import be.vlproject.egcevent.rest.domain.ErrorResponse;
import be.vlproject.egcevent.tournament.MacMahonTournamentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@RestController
public class NotifyRoundResource {

    @Autowired
    private MacMahonTournamentParser parser;

    @PostMapping("/api/notify-round")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xml = builder.parse(file.getInputStream());
        try {
            return ResponseEntity.ok(parser.parseAndSend(xml));
        } catch(Exception e) {
            return ResponseEntity.status(412).body(new ErrorResponse(e.getMessage()));
        }

    }
}
