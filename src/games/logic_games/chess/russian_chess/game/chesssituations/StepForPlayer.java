package games.logic_games.chess.russian_chess.game.chesssituations;

import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;
import games.logic_games.chess.russian_chess.game.Player;

public class StepForPlayer {
    private ChessFigure movedFigure;
    private ChessCell from;
    private ChessCell to;
    private ChessFigure beatenFigure;
    private Player player;
    private Player beatenPlayer;
    private Castling castling;
    private ChessCell beatenFrom;
    private boolean hasMoved;

    public ChessFigure getMovedFigure() {
        return movedFigure;
    }

    public ChessCell getFrom() {
        return from;
    }

    public ChessCell getTo() {
        return to;
    }

    public ChessFigure getBeatenFigure() {
        return beatenFigure;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getBeatenPlayer() {
        return beatenPlayer;
    }

    public Castling getCastling() {
        return castling;
    }

    public ChessCell getBeatenFrom() {
        return beatenFrom;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setMovedFigure(ChessFigure movedFigure) {
        this.movedFigure = movedFigure;
    }

    public void setFrom(ChessCell from) {
        this.from = from;
    }

    public void setTo(ChessCell to) {
        this.to = to;
    }

    public void setBeatenFigure(ChessFigure beatenFigure) {
        this.beatenFigure = beatenFigure;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBeatenPlayer(Player beatenPlayer) {
        this.beatenPlayer = beatenPlayer;
    }

    public void setCastling(Castling castling) {
        this.castling = castling;
    }

    public void setBeatenFrom(ChessCell beatenFrom) {
        this.beatenFrom = beatenFrom;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
