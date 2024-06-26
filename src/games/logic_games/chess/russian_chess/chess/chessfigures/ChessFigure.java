package games.logic_games.chess.russian_chess.chess.chessfigures;

import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.enums.ChessFigureName;
import games.logic_games.chess.russian_chess.game.Team;

import java.util.LinkedList;
import java.util.function.Function;

public abstract class ChessFigure {
    private ChessColour color;
    private ChessFigureName name;
    private boolean hasMoved;

    public ChessFigure() {}

    public ChessFigure(ChessColour color, ChessFigureName name) {
        this.color = color;
        this.name = name;
    }

    public LinkedList<ChessCell> whereCanGo(ChessCell startChessCell, Function<ChessColour, Team> colorToTeam) {
        return new LinkedList<>();
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public ChessColour getColor() {
        return color;
    }

    public ChessFigureName getName() {
        return name;
    }

    public void setColor(ChessColour color) {
        this.color = color;
    }

    public void setName(ChessFigureName name) {
        this.name = name;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
