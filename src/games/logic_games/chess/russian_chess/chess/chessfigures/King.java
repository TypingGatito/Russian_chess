package games.logic_games.chess.russian_chess.chess.chessfigures;

import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.enums.ChessFigureName;
import games.logic_games.chess.russian_chess.game.Team;

import java.util.*;
import java.util.function.Function;

public class King extends ChessFigure {

    public King(ChessColour colour) {
        super(colour, ChessFigureName.KING);
    }

    @Override
    public LinkedList<ChessCell> whereCanGo(ChessCell startChessCell, Function<ChessColour, Team> colorToTeam) {
        if (startChessCell.getFigureOn() != this) return null;


        LinkedList<ChessCell> whereCanGo = new LinkedList<>(startChessCell.getMap().values());

        Team thisTeam = colorToTeam.apply(getColor());

        Iterator<ChessCell> iterator = whereCanGo.iterator();
        while(iterator.hasNext()) {
            ChessCell curCell = iterator.next();
            if (curCell == null) continue;
            ChessFigure figureOn = curCell.getFigureOn();
            if (figureOn == null) continue;

            Team beatTeam = colorToTeam.apply(figureOn.getColor());
            if (thisTeam == beatTeam) iterator.remove();
        }

        return whereCanGo;
    }

}
