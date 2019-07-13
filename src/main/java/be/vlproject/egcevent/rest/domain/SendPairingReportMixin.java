package be.vlproject.egcevent.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NONE,
        include = JsonTypeInfo.As.EXISTING_PROPERTY
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendPairingReportMixin {
}
