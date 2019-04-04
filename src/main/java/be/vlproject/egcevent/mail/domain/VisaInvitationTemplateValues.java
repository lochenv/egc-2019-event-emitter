package be.vlproject.egcevent.mail.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class VisaInvitationTemplateValues {

    private final String email;
    private final String playerName;
    private final String passportNumber;
    private final String hotelName;
    private final String startDate;
    private final String endDate;

    public VisaInvitationTemplateValues(
            final String email,
            final String playerName,
            final String passportNumber,
            final String hotelName,
            final String startDate,
            final String endDate) {
        this.email = email;
        this.playerName = playerName;
        this.passportNumber = passportNumber;
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> model = new HashMap<>();
        model.put("playerName", this.playerName);
        model.put("passportNumber", this.passportNumber);
        model.put("generationDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.put("hotelName", this.hotelName);
        model.put("startDate", this.startDate);
        model.put("endDate", this.endDate);
        return model;
    }

    @Override
    public String toString() {
        return "VisaInvitationTemplateValues{" +
                "email='" + email + '\'' +
                ", playerName='" + playerName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public static class Builder {
        private String email;
        private String playerName;
        private String passportNumber;
        private String hotelName;
        private String startDate;
        private String endDate;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPlayerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Builder setPassportNumber(String passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }

        public Builder setHotelName(String hotelName) {
            this.hotelName = hotelName;
            return this;
        }

        public Builder setStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public VisaInvitationTemplateValues build() {
            return new VisaInvitationTemplateValues(
                    this.email,
                    this.playerName,
                    this.passportNumber,
                    this.hotelName,
                    this.startDate,
                    this.endDate);
        }
    }
}
