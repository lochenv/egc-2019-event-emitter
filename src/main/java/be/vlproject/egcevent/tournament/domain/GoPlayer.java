package be.vlproject.egcevent.tournament.domain;

public class GoPlayer {

    private final String lastName;
    private final String firstName;
    private final String level;

    public GoPlayer(String lastName, String firstName, String level) {
        this.lastName = lastName;
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "GoPlayer{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
