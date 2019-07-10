package be.vlproject.egcevent.tournament.domain;

import be.vlproject.egcevent.tournament.mapper.YesNoToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EgcPlayer {

    private String lastName;

    private String firstName;

    private String email;

    @JsonDeserialize(using = YesNoToBooleanDeserializer.class)
    private Boolean notification;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getNotification() {
        return Optional.ofNullable(notification).orElse(Boolean.FALSE);
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "EgcPlayer{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", notification='" + notification + '\'' +
                '}';
    }
}
