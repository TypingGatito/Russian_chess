package games.logic_games.chess.chess_project.chess.chessfigures;

import games.logic_games.chess.chess_project.chess.ChessCell;
import games.logic_games.chess.chess_project.enums.ChessColour;
import games.logic_games.chess.chess_project.enums.ChessFigureName;
import games.logic_games.chess.chess_project.game.Team;
import games.logic_games.chess.chess_project.enums.MoveDirection;

import java.util.*;
import java.util.function.Function;

public class Knight extends ChessFigure {
    private static final List<MoveDirection> directions = new LinkedList<>();
    private static final Map<MoveDirection, List<MoveDirection>> moveDirectionToSide = new HashMap<>();
    static {
        moveDirectionToSide.put(MoveDirection.UP, Arrays.asList(MoveDirection.LEFT, MoveDirection.RIGHT));
        moveDirectionToSide.put(MoveDirection.DOWN, Arrays.asList(MoveDirection.LEFT, MoveDirection.RIGHT));
        moveDirectionToSide.put(MoveDirection.RIGHT, Arrays.asList(MoveDirection.UP, MoveDirection.DOWN));
        moveDirectionToSide.put(MoveDirection.LEFT, Arrays.asList(MoveDirection.UP, MoveDirection.DOWN));

        directions.addAll(moveDirectionToSide.keySet());
    }
    public Knight(ChessColour color) {
        super(color, ChessFigureName.KNIGHT);
    }

    @Override
    public LinkedList<ChessCell> whereCanGo(ChessCell startChessCell, Function<ChessColour, Team> colorToTeam) {
        if (startChessCell.getFigureOn() != this) return null;

        LinkedList<ChessCell> whereCanGo = new LinkedList<>();

        Team thisTeam = colorToTeam.apply(getColor());

        ChessCell curCell;
        for (MoveDirection direction: directions) {
            curCell = startChessCell;
            int i = 0;
            for (; i < 2; i++) {
                curCell = curCell.getCellByDirection(direction);
                if (curCell == null) {
                    break;
                }
            }
            if (i != 2) {
                continue;
            }

            for (MoveDirection direction1: moveDirectionToSide.get(direction)) {
                ChessCell side = curCell.getCellByDirection(direction1);
                if (side == null) continue;
                if (side.getFigureOn() == null) {
                    whereCanGo.add(side);
                    continue;
                }

                Team beatTeam = colorToTeam.apply(side.getFigureOn().getColor());
                if (thisTeam != beatTeam) whereCanGo.add(side);
            }
        }


        for (MoveDirection direction: directions) {
            curCell = startChessCell.getCellByDirection(direction);
            if (curCell == null) {
                continue;
            }


            for (MoveDirection direction1: moveDirectionToSide.get(direction)) {
                ChessCell side = curCell.getCellByDirection(direction1);
                if (side == null) {
                    continue;
                }

                side = side.getCellByDirection(direction1);
                if (side == null) continue;
                if (side.getFigureOn() == null) {
                    whereCanGo.add(side);
                    continue;
                }

                Team beatTeam = colorToTeam.apply(side.getFigureOn().getColor());
                if (thisTeam != beatTeam) whereCanGo.add(side);
            }

        }

        return whereCanGo;
    }
}
