package games.logic_games.chess.chess_project.game.chesssituations;

import games.logic_games.chess.chess_project.chess.ChessCell;
import games.logic_games.chess.chess_project.chess.chessfigures.King;
import games.logic_games.chess.chess_project.chess.chessfigures.Rook;

public class Castling {
    private King king;
    private Rook rook;
    private ChessCell kingTo;
    private ChessCell rookTo;
    private ChessCell kingFrom;
    private ChessCell rookFrom;

    public Castling(King king, Rook rook, ChessCell kingTo, ChessCell rookTo,
                    ChessCell kingFrom, ChessCell rookFrom) {
        this.king = king;
        this.rook = rook;
        this.kingTo = kingTo;
        this.rookTo = rookTo;
        this.kingFrom = kingFrom;
        this.rookFrom = rookFrom;
    }

    public King getKing() {
        return king;
    }

    public Rook getRook() {
        return rook;
    }

    public ChessCell getKingTo() {
        return kingTo;
    }

    public ChessCell getRookTo() {
        return rookTo;
    }

    public ChessCell getKingFrom() {
        return kingFrom;
    }

    public ChessCell getRookFrom() {
        return rookFrom;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public void setRook(Rook rook) {
        this.rook = rook;
    }

    public void setKingTo(ChessCell kingTo) {
        this.kingTo = kingTo;
    }

    public void setRookTo(ChessCell rookTo) {
        this.rookTo = rookTo;
    }

    public void setKingFrom(ChessCell kingFrom) {
        this.kingFrom = kingFrom;
    }

    public void setRookFrom(ChessCell rookFrom) {
        this.rookFrom = rookFrom;
    }
}
