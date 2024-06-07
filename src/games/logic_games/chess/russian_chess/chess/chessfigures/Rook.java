package games.logic_games.chess.russian_chess.chess.chessfigures;

import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.enums.ChessFigureName;
import games.logic_games.chess.russian_chess.game.Team;
import games.logic_games.chess.russian_chess.enums.MoveDirection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Rook extends ChessFigure {
    public Rook(ChessColour color) {
        super(color, ChessFigureName.ROOK);
    }


    public LinkedList<ChessCell> whereCanGo(ChessCell startChessCell, Function<ChessColour, Team> colorToTeam) {
        if (startChessCell.getFigureOn() != this) return null;

        LinkedList<ChessCell> whereCanGo = new LinkedList<>();

        Team thisTeam = colorToTeam.apply(getColor());

        ChessCell curCell;
        List<MoveDirection> directionsToCheck = Arrays.asList(MoveDirection.UP, MoveDirection.DOWN,
                MoveDirection.RIGHT, MoveDirection.LEFT);
        for (MoveDirection direction: directionsToCheck) {
            curCell = startChessCell;
            while (curCell.getMap().get(direction) != null) {
                curCell = curCell.getMap().get(direction);
                ChessFigure figure = curCell.getFigureOn();
                if (figure != null) {
                    Team beatTeam = colorToTeam.apply(figure.getColor());
                    if (thisTeam != beatTeam) whereCanGo.add(curCell);
                    break;
                }

                whereCanGo.add(curCell);
            }
        }

        return whereCanGo;
    }
}
