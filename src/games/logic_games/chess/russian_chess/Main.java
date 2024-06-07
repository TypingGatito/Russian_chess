package games.logic_games.chess.russian_chess;

import games.logic_games.chess.russian_chess.game.Game;
import games.logic_games.chess.russian_chess.bot.GameWithBots;
import games.logic_games.chess.russian_chess.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        MainFrame frame = new MainFrame(new GameWithBots(game));
        frame.setVisible(true);
    }
}