package be.vlproject.egcevent.tournament.domain;

public class TournamentInfo {

    private final String name;
    private final String boardSize;
    private final String description;
    private final Integer currentRound;


    public TournamentInfo(String name, String boardSize, String description, Integer currentRound) {
        this.name = name;
        this.boardSize = boardSize;
        this.description = description;
        this.currentRound = currentRound;
    }

    public String getName() {
        return name;
    }

    public String getBoardSize() {
        return boardSize;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }
}

