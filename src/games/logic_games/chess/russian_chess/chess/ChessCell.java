package games.logic_games.chess.russian_chess.chess;

import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;
import games.logic_games.chess.russian_chess.enums.MoveDirection;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChessCell {
    private Map<MoveDirection, ChessCell> map = new LinkedHashMap<>();
    private ChessColour color;
    private ChessFigure figureOn;


    public ChessCell(ChessColour color) {
        this.color = color;
    }

    public ChessCell(ChessColour color, ChessFigure figureOn) {
        this.color = color;
        this.figureOn = figureOn;
    }

    public ChessCell(Map<MoveDirection, ChessCell> map, ChessColour color, ChessFigure figureOn) {
        this.map = map;
        this.color = color;
        this.figureOn = figureOn;
    }

    public ChessCell getCellByDirection(MoveDirection direction) {
        return map.get(direction);
    }
    public Map<MoveDirection, ChessCell> getMap() {
        return map;
    }

    public ChessColour getColor() {
        return color;
    }

    public ChessFigure getFigureOn() {
        return figureOn;
    }
    public ChessCell setCellByDirection(MoveDirection direction, ChessCell cell) {
        return map.put(direction, cell);
    }

    public void setMap(Map<MoveDirection, ChessCell> map) {
        this.map = map;
    }

    public void setColor(ChessColour color) {
        this.color = color;
    }

    public void setFigureOn(ChessFigure figureOn) {
        this.figureOn = figureOn;
    }
}
