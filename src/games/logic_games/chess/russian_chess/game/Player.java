package games.logic_games.chess.russian_chess.game;

import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private ChessColour colour;
    private List<ChessFigure> chessSet = new ArrayList<>();

    public Player(ChessColour colour) {
        this.colour = colour;
    }

    public ChessColour getColour() {
        return colour;
    }

    public List<ChessFigure> getChessSet() {
        return chessSet;
    }
    public void addFigure(ChessFigure figure) {
        chessSet.add(figure);
    }

    public void setColour(ChessColour colour) {
        this.colour = colour;
    }

    public void setChessSet(List<ChessFigure> chessSet) {
        this.chessSet = chessSet;
    }
}
