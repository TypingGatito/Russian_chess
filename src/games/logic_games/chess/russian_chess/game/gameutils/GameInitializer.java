package games.logic_games.chess.russian_chess.game.gameutils;

import games.logic_games.chess.russian_chess.chess.chessfigures.*;
import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.game.Game;
import games.logic_games.chess.russian_chess.game.Team;
import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.enums.ChessFigureName;
import games.logic_games.chess.russian_chess.enums.MoveDirection;
import games.logic_games.chess.russian_chess.game.Player;

import java.util.*;

public class GameInitializer {
    private Game game;
    private List<Team> teams;
    private HashMap<ChessFigure, ChessCell> figureCell;
    private LinkedHashMap<ChessColour, Team> colorTeam;
    private LinkedHashMap<Player, Team> playerTeam;
    private HashMap<ChessFigure, Player> figurePlayer;
    private HashMap<Player, MoveDirection> playerDirection;
    private static HashMap<ChessColour, MoveDirection> colorDirection;

    public GameInitializer(Game game) {
        this.game = game;
        figureCell = game.getFigureCell();
        colorTeam = game.getColorTeam();
        playerTeam = game.getPlayerTeam();
        playerDirection = game.getPlayerDirection();
        figurePlayer = game.getFigurePlayer();
        colorDirection = game.getColorDirection();
        teams = game.getTeams();
    }

    public void initialize() {
        createTeams();
        createFieldWithFigures();
    }

    private void createFieldWithFigures() {
        ChessCell startBig = createPart(8, 8, ChessColour.WHITE);

        //WHITE
        Player player = teams.get(0).curPlayer();
        ChessCell startPoint = createPart(8, 2, ChessColour.WHITE);

        ChessCell connectCellFromBoard = startBig;
        while (connectCellFromBoard.getCellByDirection(MoveDirection.DOWN) != null) {
            connectCellFromBoard = connectCellFromBoard.getCellByDirection(MoveDirection.DOWN);
        }
        ChessCell playerCurCell = startPoint;
        for (int i = 0; i < 8; i++) {
            connectCellFromBoard.setCellByDirection(MoveDirection.DOWN, playerCurCell);
            playerCurCell.setCellByDirection(MoveDirection.UP, connectCellFromBoard);

            ChessCell downRight = playerCurCell.getCellByDirection(MoveDirection.RIGHT);
            if (downRight != null && i != 7) {
                downRight.setCellByDirection(MoveDirection.UP_LEFT, connectCellFromBoard);
                connectCellFromBoard.setCellByDirection(MoveDirection.DOWN_RIGHT, downRight);
            }

            ChessCell upRight = connectCellFromBoard.getCellByDirection(MoveDirection.RIGHT);
            if (downRight != null && i != 7) {
                upRight.setCellByDirection(MoveDirection.DOWN_LEFT, playerCurCell);
                playerCurCell.setCellByDirection(MoveDirection.UP_RIGHT, upRight);
            }

            connectCellFromBoard = connectCellFromBoard.getCellByDirection(MoveDirection.RIGHT);
            playerCurCell = playerCurCell.getCellByDirection(MoveDirection.RIGHT);
        }

        ChessCell corner = createPart(4, 4, ChessColour.WHITE);
        ChessCell cornerCurCell = corner;

        ChessCell curPlayerBoardCell = startPoint;
        while (curPlayerBoardCell.getCellByDirection(MoveDirection.RIGHT) != null) {
            curPlayerBoardCell = curPlayerBoardCell.getCellByDirection(MoveDirection.RIGHT);
        }

        cornerCurCell.setCellByDirection(MoveDirection.LEFT, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.DOWN_LEFT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.DOWN));
        curPlayerBoardCell.setCellByDirection(MoveDirection.RIGHT, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.DOWN_RIGHT,
                cornerCurCell.getCellByDirection(MoveDirection.DOWN));

        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN);
        curPlayerBoardCell = curPlayerBoardCell.getCellByDirection(MoveDirection.DOWN);

        cornerCurCell.setCellByDirection(MoveDirection.LEFT, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.UP_LEFT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.UP));
        curPlayerBoardCell.setCellByDirection(MoveDirection.RIGHT, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.UP_RIGHT,
                cornerCurCell.getCellByDirection(MoveDirection.UP));


        List<ChessFigure> allFigures = new ArrayList<>(player.getChessSet());

        cornerCurCell = corner.getCellByDirection(MoveDirection.DOWN_RIGHT);
        ChessFigure figure = findFigureByName(allFigures, ChessFigureName.KNIGHT);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.UP_RIGHT).getCellByDirection(MoveDirection.RIGHT);
        figure = findFigureByName(allFigures, ChessFigureName.ROOK);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_LEFT).
                getCellByDirection(MoveDirection.DOWN).getCellByDirection(MoveDirection.DOWN);
        figure = findFigureByName(allFigures, ChessFigureName.BISHOP);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);

        placeFiguresOnPart(allFigures, startPoint.getCellByDirection(MoveDirection.DOWN),
                MoveDirection.RIGHT, MoveDirection.UP);
        //RED

        teams.get(0).nextPlayer();
        player = teams.get(0).curPlayer();
        startPoint = createPart(8, 2, ChessColour.WHITE);

        connectCellFromBoard = startBig;
        playerCurCell = startPoint.getCellByDirection(MoveDirection.DOWN);
        for (int i = 0; i < 8; i++) {
            connectCellFromBoard.setCellByDirection(MoveDirection.UP, playerCurCell);
            playerCurCell.setCellByDirection(MoveDirection.DOWN, connectCellFromBoard);

            ChessCell upRight = playerCurCell.getCellByDirection(MoveDirection.RIGHT);
            if (upRight != null && i != 7) {
                upRight.setCellByDirection(MoveDirection.DOWN_LEFT, connectCellFromBoard);
                connectCellFromBoard.setCellByDirection(MoveDirection.UP_RIGHT, upRight);
            }

            ChessCell downRight = connectCellFromBoard.getCellByDirection(MoveDirection.RIGHT);
            if (downRight != null && i != 7) {
                downRight.setCellByDirection(MoveDirection.UP_LEFT, playerCurCell);
                playerCurCell.setCellByDirection(MoveDirection.DOWN_RIGHT, downRight);
            }

            connectCellFromBoard = connectCellFromBoard.getCellByDirection(MoveDirection.RIGHT);
            playerCurCell = playerCurCell.getCellByDirection(MoveDirection.RIGHT);
        }
        teams.get(0).nextPlayer();

        corner = createPart(4, 4, ChessColour.WHITE);
        cornerCurCell = corner;
        while (cornerCurCell.getCellByDirection(MoveDirection.DOWN_RIGHT) != null) {
            cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_RIGHT);
        }
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.UP);

        curPlayerBoardCell = startPoint;

        cornerCurCell.setCellByDirection(MoveDirection.RIGHT, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.DOWN_RIGHT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.DOWN));
        curPlayerBoardCell.setCellByDirection(MoveDirection.LEFT, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.DOWN_LEFT,
                cornerCurCell.getCellByDirection(MoveDirection.DOWN));

        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN);
        curPlayerBoardCell = curPlayerBoardCell.getCellByDirection(MoveDirection.DOWN);

        cornerCurCell.setCellByDirection(MoveDirection.RIGHT, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.UP_RIGHT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.UP));
        curPlayerBoardCell.setCellByDirection(MoveDirection.LEFT, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.UP_LEFT,
                cornerCurCell.getCellByDirection(MoveDirection.UP));


        allFigures = new ArrayList<>(player.getChessSet());

        cornerCurCell = corner.getCellByDirection(MoveDirection.RIGHT);
        figure = findFigureByName(allFigures, ChessFigureName.BISHOP);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_RIGHT).
                getCellByDirection(MoveDirection.DOWN);
        figure = findFigureByName(allFigures, ChessFigureName.KNIGHT);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_LEFT).
                getCellByDirection(MoveDirection.LEFT);
        figure = findFigureByName(allFigures, ChessFigureName.ROOK);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);

        ChessCell start = startPoint;
        while (start.getCellByDirection(MoveDirection.RIGHT) != null) {
            start = start.getCellByDirection(MoveDirection.RIGHT);
        }
        placeFiguresOnPart(allFigures, start, MoveDirection.LEFT, MoveDirection.DOWN);

        //BLACK
        player = teams.get(1).curPlayer();
        startPoint = createPart(2, 8, ChessColour.WHITE);


        connectCellFromBoard = startBig;

        playerCurCell = startPoint.getCellByDirection(MoveDirection.RIGHT);
        for (int i = 0; i < 8; i++) {
            connectCellFromBoard.setCellByDirection(MoveDirection.LEFT, playerCurCell);
            playerCurCell.setCellByDirection(MoveDirection.RIGHT, connectCellFromBoard);

            ChessCell downLeft = playerCurCell.getCellByDirection(MoveDirection.DOWN);
            if (downLeft != null && i != 7) {
                downLeft.setCellByDirection(MoveDirection.UP_RIGHT, connectCellFromBoard);
                connectCellFromBoard.setCellByDirection(MoveDirection.DOWN_LEFT, downLeft);
            }

            ChessCell downRight = connectCellFromBoard.getCellByDirection(MoveDirection.DOWN);
            if (downRight != null && i != 7) {
                downRight.setCellByDirection(MoveDirection.UP_LEFT, playerCurCell);
                playerCurCell.setCellByDirection(MoveDirection.DOWN_RIGHT, downRight);
            }

            connectCellFromBoard = connectCellFromBoard.getCellByDirection(MoveDirection.DOWN);
            playerCurCell = playerCurCell.getCellByDirection(MoveDirection.DOWN);
        }

        corner = createPart(4, 4, ChessColour.WHITE);
        cornerCurCell = corner.getCellByDirection(MoveDirection.RIGHT).getCellByDirection(MoveDirection.RIGHT);

        curPlayerBoardCell = startPoint;
        while (curPlayerBoardCell.getCellByDirection(MoveDirection.DOWN) != null) {
            curPlayerBoardCell = curPlayerBoardCell.getCellByDirection(MoveDirection.DOWN);
        }

        cornerCurCell.setCellByDirection(MoveDirection.UP, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.UP_RIGHT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.RIGHT));
        curPlayerBoardCell.setCellByDirection(MoveDirection.DOWN, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.DOWN_RIGHT,
                cornerCurCell.getCellByDirection(MoveDirection.RIGHT));

        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.RIGHT);
        curPlayerBoardCell = curPlayerBoardCell.getCellByDirection(MoveDirection.RIGHT);

        cornerCurCell.setCellByDirection(MoveDirection.UP, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.UP_LEFT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.LEFT));
        curPlayerBoardCell.setCellByDirection(MoveDirection.DOWN, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.DOWN_LEFT,
                cornerCurCell.getCellByDirection(MoveDirection.LEFT));

        allFigures = new ArrayList<>(player.getChessSet());

        cornerCurCell = corner.getCellByDirection(MoveDirection.DOWN_RIGHT).
                getCellByDirection(MoveDirection.RIGHT);
        figure = findFigureByName(allFigures, ChessFigureName.KNIGHT);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_LEFT).
                getCellByDirection(MoveDirection.LEFT);
        figure = findFigureByName(allFigures, ChessFigureName.BISHOP);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_RIGHT).
                getCellByDirection(MoveDirection.RIGHT);
        figure = findFigureByName(allFigures, ChessFigureName.ROOK);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);

        placeFiguresOnPart(allFigures, startPoint,
                MoveDirection.DOWN, MoveDirection.RIGHT);
        //BLUE
        teams.get(1).nextPlayer();
        player = teams.get(1).curPlayer();
        startPoint = createPart(2, 8, ChessColour.WHITE);


        connectCellFromBoard = startBig;
        while (connectCellFromBoard.getCellByDirection(MoveDirection.RIGHT) != null) {
            connectCellFromBoard = connectCellFromBoard.getCellByDirection(MoveDirection.RIGHT);
        }

        playerCurCell = startPoint;
        for (int i = 0; i < 8; i++) {
            connectCellFromBoard.setCellByDirection(MoveDirection.RIGHT, playerCurCell);
            playerCurCell.setCellByDirection(MoveDirection.LEFT, connectCellFromBoard);

            ChessCell downRight = playerCurCell.getCellByDirection(MoveDirection.DOWN);
            if (downRight != null && i != 7) {
                downRight.setCellByDirection(MoveDirection.UP_LEFT, connectCellFromBoard);
                connectCellFromBoard.setCellByDirection(MoveDirection.DOWN_RIGHT, downRight);
            }

            ChessCell downLeft = connectCellFromBoard.getCellByDirection(MoveDirection.DOWN);
            if (downRight != null && i != 7) {
                downLeft.setCellByDirection(MoveDirection.UP_RIGHT, playerCurCell);
                playerCurCell.setCellByDirection(MoveDirection.DOWN_LEFT, downLeft);
            }

            connectCellFromBoard = connectCellFromBoard.getCellByDirection(MoveDirection.DOWN);
            playerCurCell = playerCurCell.getCellByDirection(MoveDirection.DOWN);
        }
        teams.get(1).nextPlayer();

        corner = createPart(4, 4, ChessColour.WHITE);
        cornerCurCell = corner.getCellByDirection(MoveDirection.DOWN)
                .getCellByDirection(MoveDirection.DOWN).getCellByDirection(MoveDirection.DOWN);

        curPlayerBoardCell = startPoint;

        cornerCurCell.setCellByDirection(MoveDirection.DOWN, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.DOWN_RIGHT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.RIGHT));
        curPlayerBoardCell.setCellByDirection(MoveDirection.UP, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.UP_RIGHT,
                cornerCurCell.getCellByDirection(MoveDirection.RIGHT));

        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.RIGHT);
        curPlayerBoardCell = curPlayerBoardCell.getCellByDirection(MoveDirection.RIGHT);

        cornerCurCell.setCellByDirection(MoveDirection.DOWN, curPlayerBoardCell);
        cornerCurCell.setCellByDirection(MoveDirection.DOWN_LEFT,
                curPlayerBoardCell.getCellByDirection(MoveDirection.LEFT));
        curPlayerBoardCell.setCellByDirection(MoveDirection.UP, cornerCurCell);
        curPlayerBoardCell.setCellByDirection(MoveDirection.UP_LEFT,
                cornerCurCell.getCellByDirection(MoveDirection.LEFT));

        allFigures = new ArrayList<>(player.getChessSet());

        cornerCurCell = corner;
        figure = findFigureByName(allFigures, ChessFigureName.ROOK);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.DOWN_RIGHT).
                getCellByDirection(MoveDirection.DOWN);
        figure = findFigureByName(allFigures, ChessFigureName.KNIGHT);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);
        cornerCurCell = cornerCurCell.getCellByDirection(MoveDirection.UP_RIGHT).
                getCellByDirection(MoveDirection.RIGHT);
        figure = findFigureByName(allFigures, ChessFigureName.BISHOP);
        cornerCurCell.setFigureOn(figure);
        figureCell.put(figure, cornerCurCell);
        allFigures.remove(figure);

        start = startPoint.getCellByDirection(MoveDirection.RIGHT);
        while (start.getCellByDirection(MoveDirection.DOWN) != null) {
            start = start.getCellByDirection(MoveDirection.DOWN);
        }
        placeFiguresOnPart(allFigures, start, MoveDirection.UP, MoveDirection.LEFT);
        //

        game.setChessBoard(startBig);
        //


    }
    private void placeFiguresOnPart(List<ChessFigure> allFigures, ChessCell startCell, MoveDirection d1,
                                    MoveDirection d2) {
        List<ChessFigureName> direction = List.of(ChessFigureName.ROOK,
                ChessFigureName.KNIGHT, ChessFigureName.BISHOP, ChessFigureName.QUEEN,
                ChessFigureName.KING, ChessFigureName.BISHOP, ChessFigureName.KNIGHT,
                ChessFigureName.ROOK);

        ChessCell curCell = startCell;

        for (ChessFigureName name: direction) {
            ChessFigure figure = findFigureByName(allFigures, name);
            curCell.setFigureOn(figure);
            figureCell.put(figure, curCell);

            allFigures.remove(figure);
            curCell = curCell.getCellByDirection(d1);
        }
        curCell = startCell.getCellByDirection(d2);
        for (ChessFigure figure: allFigures) {
            curCell.setFigureOn(figure);
            figureCell.put(figure, curCell);
            curCell = curCell.getCellByDirection(d1);
        }
    }
    private ChessFigure findFigureByName(List<ChessFigure> figures, ChessFigureName name) {
        for (ChessFigure figure: figures) {
            if (figure.getName() == name) return figure;
        }

        return null;
    }
    private ChessCell createPart(int width, int height, ChessColour startColour) {
        HashMap<ChessColour, ChessColour> swapColor = new HashMap<>();
        swapColor.put(ChessColour.BLACK, ChessColour.WHITE);
        swapColor.put(ChessColour.WHITE, ChessColour.BLACK);

        ChessCell startPart = new ChessCell(startColour);

        ChessCell curCell;
        ChessCell right;
        ChessCell down = startPart;
        ChessCell up;
        ChessCell upRight;
        ChessCell upLeft;
        for (int i = 1; i <= height; i++) {
            curCell = down;

            up = curCell.getCellByDirection(MoveDirection.UP);
            if (up != null) {
                upRight = up.getCellByDirection(MoveDirection.RIGHT);
                curCell.setCellByDirection(MoveDirection.UP_RIGHT, upRight);
                up.setCellByDirection(MoveDirection.DOWN, curCell);

                upRight.setCellByDirection(MoveDirection.DOWN_LEFT, curCell);
            }

            if (i != height) {
                down = new ChessCell(swapColor.get(curCell.getColor()));
                down.setCellByDirection(MoveDirection.UP, curCell);
                curCell.setCellByDirection(MoveDirection.DOWN, down);
            }

            for (int j = 1; j < width; j++) {
                right = new ChessCell(swapColor.get(curCell.getColor()));

                right.setCellByDirection(MoveDirection.LEFT, curCell);
                curCell.setCellByDirection(MoveDirection.RIGHT, right);

                upLeft = curCell.getCellByDirection(MoveDirection.UP);
                if (upLeft != null) {
                    right.setCellByDirection(MoveDirection.UP_LEFT, upLeft);
                    upLeft.setCellByDirection(MoveDirection.DOWN_RIGHT, right);
                }

                up = curCell.getCellByDirection(MoveDirection.UP_RIGHT);
                if (up != null) {
                    right.setCellByDirection(MoveDirection.UP, up);
                    up.setCellByDirection(MoveDirection.DOWN, right);
                }

                up = right.getCellByDirection(MoveDirection.UP);
                if (up != null) {
                    upRight = up.getCellByDirection(MoveDirection.RIGHT);
                    if (upRight != null) {
                        right.setCellByDirection(MoveDirection.UP_RIGHT, upRight);
                        upRight.setCellByDirection(MoveDirection.DOWN_LEFT, right);
                    }
                }

                curCell = right;
            }

        }

        return startPart;
    }


    private void createTeams() {
        Team team = new Team();
        teams.add(team);
        game.setCurTeam(team);
        colorTeam.put(ChessColour.WHITE, team);
        colorTeam.put(ChessColour.RED, team);

        team = new Team();
        teams.add(team);
        colorTeam.put(ChessColour.BLACK, team);
        colorTeam.put(ChessColour.BLUE, team);

        for (ChessColour curColor: colorTeam.keySet()) {
            Player player = new Player(curColor);
            playerDirection.put(player, colorDirection.get(curColor));
            team = colorTeam.get(curColor);
            team.addPlayer(player);
            //if (curColor != ChessColour.WHITE && curColor != ChessColour.BLACK) continue;
            List<ChessFigure> chessSet = createChessSetForPlayer(player);
            player.setChessSet(chessSet);

            for (ChessFigure figure: chessSet) {
                figurePlayer.put(figure, player);
                playerTeam.put(player, team);
            }
        }
    }
    public List<ChessFigure> createChessSetForPlayer(Player player) {
        ChessColour colour = player.getColour();
        List<ChessFigure> chessSet = new LinkedList<>();

        chessSet.add(new King(colour));
        chessSet.add(new Queen(colour));

        for (int i = 0; i < 3; i++) {
            chessSet.add(new Bishop(colour));
            chessSet.add(new Knight(colour));
            chessSet.add(new Rook(colour));
        }

        for (int i = 0; i < 8; i++) {
            chessSet.add(new Pawn(colour, playerDirection.get(player)));
        }

        return chessSet;
    }
}
