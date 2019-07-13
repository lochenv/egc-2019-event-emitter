package be.vlproject.egcevent.rest.domain;

import be.vlproject.egcevent.tournament.domain.GoPlayer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NONE,
        include = JsonTypeInfo.As.EXISTING_PROPERTY
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SendPairingReportMixin {

    @JsonSerialize(using = LightGoPlayerListSerializer.class)
    public abstract List<GoPlayer> getSucceeded();

    @JsonSerialize(using = LightGoPlayerListSerializer.class)
    public abstract List<GoPlayer> getInError();
}
