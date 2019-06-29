package be.vlproject.egcevent.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/players")
public class PlayerResource {

    private String subscribersUri = "http://egc2019.eu/congress/registered-participants?" +
            "export=true&token=rJV2P0HxWp8XaofbgN4dy2hc26ijB5WZz0nsKLpz7q2nA";

    private String egdUrl = "http://www.europeangodatabase.eu/EGD/GetPlayerDataByPIN.php";
    @GetMapping
    public ResponseEntity<?> getRegisteredPlayers() {
        RestTemplate playerTemplate = new RestTemplate();
        return playerTemplate.getForEntity(subscribersUri, String.class);
    }

    @GetMapping(path = "/egd")
    public ResponseEntity<?> getRegisteredPlayersEgd(@RequestParam("pin") String egdPin) {
        RestTemplate egdTemplate = new RestTemplate();
        UriComponentsBuilder egdUriBuilder = UriComponentsBuilder.fromHttpUrl(egdUrl)
                .queryParam("pin", egdPin);
        return egdTemplate.getForEntity(egdUriBuilder.toUriString(), String.class);
    }
}
