package games.logic_games.chess.chess_project.chess.chessfigures;

import games.logic_games.chess.chess_project.chess.ChessCell;
import games.logic_games.chess.chess_project.enums.ChessColour;
import games.logic_games.chess.chess_project.enums.ChessFigureName;
import games.logic_games.chess.chess_project.game.Team;
import games.logic_games.chess.chess_project.enums.MoveDirection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Queen extends ChessFigure {
    public Queen(ChessColour color) {
        super(color, ChessFigureName.QUEEN);
    }

    @Override
    public LinkedList<ChessCell> whereCanGo(ChessCell startChessCell, Function<ChessColour, Team> colorToTeam) {
        if (startChessCell.getFigureOn() != this) return null;

        LinkedList<ChessCell> whereCanGo = new LinkedList<>();

        Team thisTeam = colorToTeam.apply(getColor());

        ChessCell curCell = startChessCell;
        List<MoveDirection> directionsToCheck = Arrays.asList(MoveDirection.UP, MoveDirection.DOWN,
                MoveDirection.RIGHT, MoveDirection.LEFT,
                MoveDirection.UP_RIGHT, MoveDirection.UP_LEFT,
                MoveDirection.DOWN_RIGHT, MoveDirection.DOWN_LEFT);
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
