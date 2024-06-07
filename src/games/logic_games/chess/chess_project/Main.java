package games.logic_games.chess.chess_project;

import games.logic_games.chess.chess_project.game.Game;
import games.logic_games.chess.chess_project.bot.GameWithBots;
import games.logic_games.chess.chess_project.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        MainFrame frame = new MainFrame(new GameWithBots(game));
        frame.setVisible(true);
    }
}