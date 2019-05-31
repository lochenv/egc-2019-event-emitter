package be.vlproject.egcevent.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class UserAuthenticationResponseDtoMixin {
}
