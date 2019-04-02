package be.vlproject.egcevent.mail.domain;

public class PairingTemplateValues {

    private final String email;
    private final String firstName;
    private final String lastName;

    private final String opponentFirstName;
    private final String opponentLastName;
    private final String opponentLevel;

    private final String table;
    private final String color;

    private final String tournamentName;
    private final String boardSize;
    private final String roundNumber;
    private final String description;

    public PairingTemplateValues(
            final String email,
            final String firstName,
            final String lastName,
            final String opponentFirstName,
            final String opponentLastName,
            final String opponentLevel,
            final String table,
            final String colour,
            final String tournamentName,
            final String boardSize,
            final String roundNumber,
            final String description) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.opponentFirstName = opponentFirstName;
        this.opponentLastName = opponentLastName;
        this.opponentLevel = opponentLevel;
        this.table = table;
        this.color = colour;
        this.tournamentName = tournamentName;
        this.boardSize = boardSize;
        this.roundNumber = roundNumber;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOpponentFirstName() {
        return opponentFirstName;
    }

    public String getOpponentLastName() {
        return opponentLastName;
    }

    public String getTable() {
        return table;
    }

    public String getColor() {
        return color;
    }

    public String getOpponentLevel() {
        return opponentLevel;
    }

    public String getBoardSize() {
        return boardSize;
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public String getDescription() {
        return description;
    }
}
