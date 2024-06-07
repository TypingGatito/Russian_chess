package games.logic_games.chess.russian_chess.bot;

import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.game.Game;
import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;
import games.logic_games.chess.russian_chess.game.Player;

import java.util.HashMap;
import java.util.List;

public class GameWithBots {
    private Game game;
    private HashMap<Player, BotPlayer> playerToBot = new HashMap<>();
    private boolean isBotToPlay;

    public GameWithBots(Game game) {
        this.game = game;

        for (Player player: game.getPlayerTeam().keySet()) {
            if (player.getColour() == ChessColour.WHITE) continue;
            BotPlayer botPlayer = new BotPlayer(player);
            playerToBot.put(player, botPlayer);
            isBotToPlay = false;
        }
    }

/*    public void botPlay() {
        bot curBot;
        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) {
            curBot = playerToBot.get(game.getCurTeam().curPlayer());

            curBot.makeStep(game);
        }

        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) isBotToPlay = true;
        else isBotToPlay = false;
    }*/

    public void botChoose() {
        BotPlayer curBot;
        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) {
            curBot = playerToBot.get(game.getCurTeam().curPlayer());

            curBot.chooseFigureInGame(game);
        }
    }

    public void botMoveFigure() {
        BotPlayer curBot;
        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) {
            curBot = playerToBot.get(game.getCurTeam().curPlayer());

            curBot.moveChosenFigure(game);
        }

        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) isBotToPlay = true;
        else isBotToPlay = false;
    }

    public boolean moveChosenFigureTo(ChessCell moveTo) {
        boolean ans = game.moveChosenFigureTo(moveTo);
        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) isBotToPlay = true;
        return ans;
    }

    public void stepBack() {
        game.stepBack();

        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) isBotToPlay = true;
        else isBotToPlay = false;
    }
    public void stepFromSaved() {
        game.stepFromSaved();

        if (playerToBot.keySet().contains(game.getCurTeam().curPlayer())) isBotToPlay = true;
        else isBotToPlay = false;
    }
    public void chooseFigure(ChessFigure figure) {
        game.chooseFigure(figure);
    }
    public List<ChessCell> whereCanChosenGo() {
        return game.whereCanChosenGo();
    }

    public void setBotToPlay(boolean botToPlay) {
        isBotToPlay = botToPlay;
    }

    public ChessFigure getChosenFigure() {
        return game.getChosenFigure();
    }

    public ChessCell getChessBoard() {
        return game.getChessBoard();
    }

    public boolean isBotToPlay() {
        return isBotToPlay;
    }

    public ChessColour getCurColor() {
        return game.getCurTeam().curPlayer().getColour();
    }
}
