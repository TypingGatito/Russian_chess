package games.logic_games.chess.chess_project.game;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players = new ArrayList<>();
    private int indexOfCurPlayer = 0;

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player curPlayer() {
        return players.get(indexOfCurPlayer);
    }

    public void nextPlayer() {
        if (indexOfCurPlayer == players.size() - 1) indexOfCurPlayer = 0;
                else indexOfCurPlayer++;
    }

    public void prevPlayer() {
        if (indexOfCurPlayer == 0) indexOfCurPlayer = players.size() - 1;
        else indexOfCurPlayer--;
    }
}
