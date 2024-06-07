package games.logic_games.chess.russian_chess.gui;

import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.enums.ChessFigureName;
import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;
import games.logic_games.chess.russian_chess.enums.MoveDirection;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ChessDrawer {
    private static HashMap<ChessColour, Color> chessColorToColor;

    static {
        chessColorToColor = new HashMap<>();
        chessColorToColor.put(ChessColour.WHITE, Color.white);
        chessColorToColor.put(ChessColour.BLACK, Color.black);
        chessColorToColor.put(ChessColour.RED, Color.red);
        chessColorToColor.put(ChessColour.BLUE, Color.blue);
    }
    public static void drawCell(Graphics2D g2, Color color, int width, int height,
                                List<MoveDirection> border) {
        Color oldColor = g2.getColor();

        g2.setColor(color);
        g2.fillRect(0, 0, width, height);

        g2.setColor(Color.BLACK);
        for(MoveDirection direction: border) {
            drawBorder(g2, width, height, direction);
        }

        g2.setColor(oldColor);
    }
    private static void drawBorder(Graphics2D g2, int width, int height, MoveDirection direction) {
        Stroke oldStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(3f));

        switch (direction) {
            case UP -> g2.drawLine(0, 1, width, 1);
            case DOWN ->  g2.drawLine(0, height - 1, width, height - 1);
            case RIGHT ->  g2.drawLine(width - 1, 0, width - 1, height);
            case LEFT ->  g2.drawLine(1, 0, 1, height);
        }

        g2.setStroke(oldStroke);
    }
    public static void drawToBeatCell(Graphics2D g2, Color color, int width, int height) {
        Color oldColor = g2.getColor();

        g2.setColor(color);
        g2.fillRect(0, 0, width, height);

        g2.setColor(new Color(91,255,91));
        g2.fillOval(15, 15, width/3, height/3);

        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, width, height);

        g2.setColor(oldColor);
    }
    public static void drawChosenFigureCell(Graphics2D g2, Color color, int width, int height) {
        Color oldColor = g2.getColor();

        g2.setColor(color);
        g2.fillRect(0, 0, width, height);

        g2.setColor(new Color(91,255,91));
        g2.fillOval(15, 15, width/3, height/3);

        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, width, height);

        g2.setColor(oldColor);
    }

    public static void drawFigure(ChessFigure figure, Graphics2D g2) {
        Color oldColor = g2.getColor();

        if (figure == null) return;
        ChessFigureName name = figure.getName();
        ChessColour colour = figure.getColor();
        switch (name) {
            case KING -> drawKing(g2, chessColorToColor.get(colour));
            case QUEEN -> drawQueen(g2, chessColorToColor.get(colour));
            case ROOK -> drawRook(g2, chessColorToColor.get(colour));
            case BISHOP -> drawBishop(g2, chessColorToColor.get(colour));
            case KNIGHT -> drawKnight(g2, chessColorToColor.get(colour));
            case PAWN -> drawPawn(g2, chessColorToColor.get(colour));
        }

        g2.setColor(oldColor);
    }
    private static void drawKing(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Serif", Font.PLAIN, 25));
        g2.drawString(String.valueOf("\u2654"), 10, 30);
    }

    private static void drawQueen(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Serif", Font.PLAIN, 25));
        g2.drawString(String.valueOf("\u2655"), 10, 30);
    }
    private static void drawRook(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Serif", Font.PLAIN, 25));
        g2.drawString(String.valueOf("\u2656"), 10, 30);
    }
    private static void drawBishop(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Serif", Font.PLAIN, 25));
        g2.drawString(String.valueOf("\u2657"), 10, 30);
    }
    private static void drawKnight(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Serif", Font.PLAIN, 25));
        g2.drawString(String.valueOf("\u2658"), 10, 30);
    }
    private static void drawPawn(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Serif", Font.PLAIN, 25));
        g2.drawString(String.valueOf("\u2659"), 10, 30);
    }
}
