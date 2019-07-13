package be.vlproject.egcevent.tournament.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SendPairingReport {

    private final List<GoPlayer> succeeded;
    private final List<GoPlayer> inError;

    public SendPairingReport() {
        this.succeeded = new LinkedList<>();
        this.inError = new LinkedList<>();
    }

    public List<GoPlayer> getSucceeded() {
        return succeeded;
    }

    public List<GoPlayer> getInError() {
        return inError;
    }

    public void addSucceeded(GoPlayer player) {
        this.succeeded.add(player);
    }

    public void addInError(GoPlayer player) {
        this.inError.add(player);
    }
}
