package games.logic_games.chess.chess_project.chess.chessfigures;

import games.logic_games.chess.chess_project.chess.ChessCell;
import games.logic_games.chess.chess_project.enums.ChessColour;
import games.logic_games.chess.chess_project.enums.ChessFigureName;
import games.logic_games.chess.chess_project.game.Team;
import games.logic_games.chess.chess_project.enums.MoveDirection;

import java.util.*;
import java.util.function.Function;

public class Pawn extends ChessFigure {
    private MoveDirection direction;
    private static final Map<MoveDirection, List<MoveDirection>> moveDirectionToBeatDirection;
    static {
        moveDirectionToBeatDirection = new HashMap<>();
        moveDirectionToBeatDirection.put(MoveDirection.UP,
                Arrays.asList(MoveDirection.UP_RIGHT, MoveDirection.UP_LEFT));
        moveDirectionToBeatDirection.put(MoveDirection.DOWN,
                Arrays.asList(MoveDirection.DOWN_RIGHT, MoveDirection.DOWN_LEFT));
        moveDirectionToBeatDirection.put(MoveDirection.RIGHT,
                Arrays.asList(MoveDirection.UP_RIGHT, MoveDirection.DOWN_RIGHT));
        moveDirectionToBeatDirection.put(MoveDirection.LEFT,
                Arrays.asList(MoveDirection.UP_LEFT, MoveDirection.DOWN_LEFT));
    }


    public Pawn(ChessColour color, MoveDirection direction) {
        super(color, ChessFigureName.PAWN);
        this.direction = direction;
    }

    @Override
    public LinkedList<ChessCell> whereCanGo(ChessCell startChessCell, Function<ChessColour, Team> colorToTeam) {
        if (startChessCell.getFigureOn() != this) return null;

        LinkedList<ChessCell> whereCanGo = new LinkedList<>();

        Team thisTeam = colorToTeam.apply(getColor());

        ChessCell next = startChessCell.getCellByDirection(direction);
        if (next == null) return whereCanGo;
        if (next.getFigureOn() == null) {
            whereCanGo.add(next);
        }

        ChessCell nextNext = next.getCellByDirection(direction);
        if (nextNext != null && nextNext.getFigureOn() == null && !isHasMoved() && next.getFigureOn() == null) {
            whereCanGo.add(nextNext);
        }

        ChessCell cellToBeatOn;

        List<MoveDirection> directionsToBeat = moveDirectionToBeatDirection.get(direction);

        for (MoveDirection direction: directionsToBeat) {
            cellToBeatOn = startChessCell.getCellByDirection(direction);
            if (cellToBeatOn == null) continue;
            ChessFigure figure = cellToBeatOn.getFigureOn();
            if (figure == null) continue;

            Team beatTeam = colorToTeam.apply(figure.getColor());
            if (thisTeam != beatTeam) whereCanGo.add(cellToBeatOn);
        }

        return whereCanGo;
    }


    public MoveDirection getDirection() {
        return direction;
    }


    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }
}
