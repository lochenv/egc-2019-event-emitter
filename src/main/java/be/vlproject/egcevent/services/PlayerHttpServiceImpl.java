package be.vlproject.egcevent.services;

import be.vlproject.egcevent.tournament.domain.EgcPlayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerHttpServiceImpl implements PlayerHttpService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${php.backend.uri}")
    private String phpBackendUri;

    @Override
    public List<EgcPlayer> findAll() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet readPlayersRequest = new HttpGet(
                String.format("%sread.php", phpBackendUri));

        Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .ifPresent(request ->
                        readPlayersRequest.addHeader("origin",
                                StringUtils.replace(
                                        request.getRequestURL().toString(),
                                        request.getRequestURI(),
                                        ""
                                )));
        HttpResponse response = client.execute(readPlayersRequest);
        final List<EgcPlayer> egcPlayers;
        if (response.getStatusLine().getStatusCode() == 200) { // OK
            egcPlayers = objectMapper.readValue(
                    response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, EgcPlayer.class));
        } else {
            egcPlayers = Collections.emptyList();
        }
        return egcPlayers;
    }
}
