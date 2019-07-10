package be.vlproject.egcevent.tournament.domain;

public class GoPlayer {

    private final String lastName;
    private final String firstName;
    private final String level;
    private String email;

    public GoPlayer(
            final String lastName,
            final String firstName,
            final String email,
            final String level) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.level = level;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLevel() {
        return level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "GoPlayer{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
