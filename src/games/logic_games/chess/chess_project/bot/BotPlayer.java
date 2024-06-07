package games.logic_games.chess.chess_project.bot;

import games.logic_games.chess.chess_project.chess.ChessCell;
import games.logic_games.chess.chess_project.game.Game;
import games.logic_games.chess.chess_project.chess.chessfigures.ChessFigure;
import games.logic_games.chess.chess_project.game.Player;

import java.util.List;
import java.util.Random;

public class BotPlayer {
    private Player player;
    private Random random;

    public BotPlayer(Player player) {
        random = new Random();

        this.player = player;
    }

    public void makeStep(Game game) {
        chooseFigureInGame(game);
        moveChosenFigure(game);
    }

    public void chooseFigureInGame(Game game) {
        if (game.getCurTeam().curPlayer() != player) return;

        List<ChessFigure> figures = player.getChessSet();

        ChessFigure chosenFigure = figures.get(random.nextInt(figures.size()));

        game.setChosenFigure(chosenFigure);
    }

    public void moveChosenFigure(Game game) {
        if (game.getCurTeam().curPlayer() != player) return;
        if (game.getChosenFigure() == null) return;

        List<ChessCell> whereCanGo = game.whereCanChosenGo();

        if (whereCanGo.size() == 0) return;

        ChessCell chosenCell = whereCanGo.get(random.nextInt(whereCanGo.size()));

        game.moveChosenFigureTo(chosenCell);
    }
}
