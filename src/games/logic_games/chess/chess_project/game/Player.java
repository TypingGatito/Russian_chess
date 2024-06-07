package games.logic_games.chess.chess_project.game;

import games.logic_games.chess.chess_project.enums.ChessColour;
import games.logic_games.chess.chess_project.chess.chessfigures.ChessFigure;

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
