package be.vlproject.egcevent.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NONE,
        include = JsonTypeInfo.As.EXISTING_PROPERTY
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class JwtValidateResponseDtoMixin {

    @JsonCreator
    JwtValidateResponseDtoMixin(boolean valid) {}
}
