package be.vlproject.egcevent.tournament.domain;

public class GoPlayer {

    private final String lastName;
    private final String firstName;
    private final String email;
    private final String level;

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

    public String getEmail() {
        return email;
    }

    public String getLevel() {
        return level;
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
