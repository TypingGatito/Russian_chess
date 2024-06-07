package games.logic_games.chess.russian_chess.game.gameutils;

import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.game.Game;
import games.logic_games.chess.russian_chess.game.Team;
import games.logic_games.chess.russian_chess.game.chesssituations.Castling;
import games.logic_games.chess.russian_chess.game.chesssituations.StepForPlayer;
import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;
import games.logic_games.chess.russian_chess.enums.MoveDirection;
import games.logic_games.chess.russian_chess.game.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GameAct {
    private Game game;
    private List<Team> teams;
    private HashMap<ChessFigure, ChessCell> figureCell;
    private LinkedHashMap<ChessColour, Team> colorTeam;
    private LinkedHashMap<Player, Team> playerTeam;
    private HashMap<ChessFigure, Player> figurePlayer;
    private HashMap<Player, MoveDirection> playerDirection;
    private static HashMap<ChessColour, MoveDirection> colorDirection;

    public GameAct(Game game) {
        this.game = game;
        figureCell = game.getFigureCell();
        colorTeam = game.getColorTeam();
        playerTeam = game.getPlayerTeam();
        playerDirection = game.getPlayerDirection();
        figurePlayer = game.getFigurePlayer();
        colorDirection = game.getColorDirection();
        teams = game.getTeams();
    }

    public void castle(Castling castling) {
        ChessCell moveFrom = figureCell.get(castling.getKing());
        castling.getKingTo().setFigureOn(castling.getKing());
        figureCell.put(castling.getKing(), castling.getKingTo());
        moveFrom.setFigureOn(null);

        moveFrom = figureCell.get(castling.getRook());
        castling.getRookTo().setFigureOn(castling.getRook());
        figureCell.put(castling.getRook(), castling.getRookTo());
        moveFrom.setFigureOn(null);

        castling.getRook().setHasMoved(true);
        castling.getKing().setHasMoved(true);
    }

    public void step (StepForPlayer step) {
        if (step.getCastling() != null) {
            castle(step.getCastling());
            return;
        }

        step.getTo().setFigureOn(step.getMovedFigure());
        figureCell.put(step.getMovedFigure(), step.getTo());
        step.getFrom().setFigureOn(null);
        step.getMovedFigure().setHasMoved(true);

        if (step.getBeatenFigure() == null) return;
        //delete figure from maps
        figureCell.remove(step.getBeatenFigure());
        Player player = figurePlayer.get(step.getBeatenFigure());
        player.getChessSet().remove(step.getBeatenFigure());
        figurePlayer.remove(step.getBeatenFigure());
    }

    public void stepBack (StepForPlayer step) {
        if (step.getCastling() != null) {
            castleBack(step.getCastling());
            return;
        }

        step.getTo().setFigureOn(step.getBeatenFigure());
        step.getFrom().setFigureOn(step.getMovedFigure());
        figureCell.put(step.getMovedFigure(), step.getFrom());

        step.getMovedFigure().setHasMoved(step.isHasMoved());

        if (step.getBeatenFigure() == null) return;
        //add figure from maps
        figureCell.put(step.getBeatenFigure(), step.getBeatenFrom());
        step.getBeatenPlayer().getChessSet().add(step.getBeatenFigure());
        figurePlayer.put(step.getBeatenFigure(), step.getBeatenPlayer());
    }

    public void castleBack(Castling castling) {

        castling.getKingFrom().setFigureOn(castling.getKing());
        figureCell.put(castling.getKing(), castling.getKingFrom());
        castling.getKingTo().setFigureOn(null);

        castling.getRookFrom().setFigureOn(castling.getRook());
        figureCell.put(castling.getRook(), castling.getRookFrom());
        castling.getRookTo().setFigureOn(null);
    }
}
