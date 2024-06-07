package games.logic_games.chess.russian_chess.game;

import games.logic_games.chess.russian_chess.chess.chessfigures.ChessFigure;
import games.logic_games.chess.russian_chess.chess.chessfigures.King;
import games.logic_games.chess.russian_chess.chess.chessfigures.Rook;
import games.logic_games.chess.russian_chess.enums.ChessColour;
import games.logic_games.chess.russian_chess.game.chesssituations.Castling;
import games.logic_games.chess.russian_chess.game.chesssituations.StepForPlayer;
import games.logic_games.chess.russian_chess.chess.ChessCell;
import games.logic_games.chess.russian_chess.enums.ChessFigureName;
import games.logic_games.chess.russian_chess.enums.MoveDirection;
import games.logic_games.chess.russian_chess.game.gameutils.GameAct;
import games.logic_games.chess.russian_chess.game.gameutils.GameInitializer;

import java.util.*;
import java.util.function.Function;

public class Game {
    private Stack<StepForPlayer> stepsForward = new Stack<>();
    private Stack<StepForPlayer> stepsForSave = new Stack<>();
    private StepForPlayer curStep = new StepForPlayer();
    private GameAct gameAct;
    private ChessCell chessBoard;

    private Team curTeam;
    private List<Team> teams = new ArrayList<>();
    private int indexOfCurTeam;

    private int roundsDone = 0;
    private ChessFigure chosenFigure;
    //
    private HashMap<ChessFigure, ChessCell> figureCell = new HashMap<>();
    private LinkedHashMap<ChessColour, Team> colorTeam = new LinkedHashMap<>();
    private LinkedHashMap<Player, Team> playerTeam = new LinkedHashMap<>();
    private HashMap<ChessFigure, Player> figurePlayer = new HashMap<>();
    private HashMap<Player, MoveDirection> playerDirection = new HashMap<>();
    private static HashMap<ChessColour, MoveDirection> colorDirection = new HashMap<>();
    //

    static {
        colorDirection.put(ChessColour.WHITE, MoveDirection.UP);
        colorDirection.put(ChessColour.BLACK, MoveDirection.RIGHT);
        colorDirection.put(ChessColour.RED, MoveDirection.DOWN);
        colorDirection.put(ChessColour.BLUE, MoveDirection.LEFT);
    }

    public Game() {
        new GameInitializer(this).initialize();
        gameAct = new GameAct(this);
    }

    public void chooseFigure(ChessFigure figure) {
        if (curTeam.curPlayer() != figurePlayer.get(figure)) {
            chosenFigure = null;
            return;
        }

        chosenFigure = figure;
    }

    public List<ChessCell> whereCanChosenGo() {
        if (chosenFigure == null) return new LinkedList<>();
        Function<ChessColour, Team> colorToTeam = (color) -> colorTeam.get(color);
        List<ChessCell> whereCanGo = chosenFigure.whereCanGo(figureCell.get(chosenFigure), colorToTeam);

        if (chosenFigure.getName() == ChessFigureName.KING) {
            List<Castling> castlings = tryCastling(chosenFigure);
            for (Castling castling: castlings) {
                whereCanGo.add(castling.getKingTo());
            }
        }

        return whereCanGo;
    }
    private List<Castling> tryCastling(ChessFigure figureK) {
        List<Castling> castlings = new ArrayList<>();
        if (figureK.getName() != ChessFigureName.KING) return castlings;
        if (figureK.isHasMoved()) return castlings;


        List<MoveDirection> directions = List.of(MoveDirection.UP, MoveDirection.DOWN,
                MoveDirection.LEFT, MoveDirection.RIGHT);
        HashMap<MoveDirection, MoveDirection> backDirections = new HashMap<>();
        backDirections.put(MoveDirection.RIGHT, MoveDirection.LEFT);
        backDirections.put(MoveDirection.LEFT, MoveDirection.RIGHT);
        backDirections.put(MoveDirection.UP, MoveDirection.DOWN);
        backDirections.put(MoveDirection.DOWN, MoveDirection.UP);


        List<Rook> rooksNotMoved = new ArrayList<>();
        for (ChessFigure figure: curTeam.curPlayer().getChessSet()) {
            if (figure.getName() != ChessFigureName.ROOK) continue;
            if (figure.isHasMoved()) continue;
            rooksNotMoved.add((Rook) figure);
        }


        ChessCell curCell;
        for (Rook rook: rooksNotMoved) {
            curCell = figureCell.get(rook);
            for (MoveDirection direction : directions) {
                while (curCell.getCellByDirection(direction) != null) {
                    curCell = curCell.getCellByDirection(direction);
                    ChessFigure figure = curCell.getFigureOn();

                    MoveDirection back = backDirections.get(direction);
                    if (figure == figureK) {
                        Castling castling = new Castling((King) figureK, rook,
                                curCell.getCellByDirection(back).getCellByDirection(back),
                                curCell.getCellByDirection(back), figureCell.get(figureK),
                                figureCell.get(rook));
                        castlings.add(castling);
                        break;
                    }

                    if (figure != null) {
                        curCell = figureCell.get(rook);
                        break;
                    }
                }
            }
        }

        return castlings;
    }

    public boolean moveChosenFigureTo(ChessCell moveTo) {
        if (chosenFigure == null) return false;
        if (!whereCanChosenGo().contains(moveTo)) return false;
        Player curPlayer = curTeam.curPlayer();

        if (figurePlayer.get(chosenFigure) != curPlayer) return false;

        ChessFigure beatenFigure = moveTo.getFigureOn();
        moveFigureTo(chosenFigure, moveTo, curStep);

        //сходили. следующий игрок
        chosenFigure = null;

        gameAct.step(curStep);
        stepsForward.push(curStep);

        curStep = new StepForPlayer();
        stepsForSave.clear();

        if (beatenFigure != null) {
            if (beatenFigure.getName() == ChessFigureName.KING) {

                stepsForward.clear();
                stepsForSave.clear();
            }
        }
        nextTeam();
        return true;
    }

    private void moveFigureTo(ChessFigure figure, ChessCell moveTo, StepForPlayer curStep) {
        if (figure.getName() == ChessFigureName.KING) {
            List<Castling> castlings = tryCastling(figure);

            for (Castling castling: castlings) {
                if (castling.getKingTo() == moveTo) {
                    curStep.setCastling(castling);
                    return;
                }
            }
        }

        ChessCell moveFrom = figureCell.get(figure);
        ChessFigure beatenFigure = moveTo.getFigureOn();


        curStep.setMovedFigure(figure);
        curStep.setPlayer(figurePlayer.get(figure));
        curStep.setTo(moveTo);
        curStep.setFrom(moveFrom);
        curStep.setHasMoved(figure.isHasMoved());


        if (beatenFigure == null) return;
        //delete figure from maps
        Player player = figurePlayer.get(beatenFigure);

        curStep.setBeatenFigure(beatenFigure);
        curStep.setBeatenPlayer(player);
        curStep.setBeatenFrom(figureCell.get(beatenFigure));

    }


    private void nextTeam() {
        curTeam.nextPlayer();
        if (indexOfCurTeam == teams.size() - 1) indexOfCurTeam = 0;
        else indexOfCurTeam++;
        curTeam = teams.get(indexOfCurTeam);


        if (curTeam.curPlayer().getChessSet().size() == 0) {
            nextTeam();
        }
    }

    private void prevTeam() {
        if (indexOfCurTeam == 0) indexOfCurTeam = teams.size() - 1;
        else indexOfCurTeam--;
        curTeam = teams.get(indexOfCurTeam);
        curTeam.prevPlayer();

        if (curTeam.curPlayer().getChessSet().size() == 0) {
            nextTeam();
        }
        chosenFigure = null;
    }

    public void stepBack() {
        if (stepsForward.size() == 0) return;

        StepForPlayer backStep = stepsForward.pop();
        stepsForSave.push(backStep);
        gameAct.stepBack(backStep);
        prevTeam();
    }

    public void stepFromSaved() {
        if (stepsForSave.size() == 0) return;

        StepForPlayer step = stepsForSave.pop();
        stepsForward.push(step);
        gameAct.step(step);
        nextTeam();
    }

//    private boolean containsKing(Collection<ChessFigure> figures) {
//        for (ChessFigure figure: figures) if (figure.getName() == ChessFigureName.KING) return true;
//        return false;
//    }



    public Team getCurTeam() {
        return curTeam;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int getIndexOfCurTeam() {
        return indexOfCurTeam;
    }

    public int getRoundsDone() {
        return roundsDone;
    }

    public HashMap<ChessFigure, ChessCell> getFigureCell() {
        return figureCell;
    }

    public LinkedHashMap<Player, Team> getPlayerTeam() {
        return playerTeam;
    }

    public HashMap<ChessFigure, Player> getFigurePlayer() {
        return figurePlayer;
    }

    public HashMap<Player, MoveDirection> getPlayerDirection() {
        return playerDirection;
    }

    public static HashMap<ChessColour, MoveDirection> getColorDirection() {
        return colorDirection;
    }

    public ChessCell getChessBoard() {
        return chessBoard;
    }

    public LinkedHashMap<ChessColour, Team> getColorTeam() {
        return colorTeam;
    }

    public ChessFigure getChosenFigure() {
        return chosenFigure;
    }

    public void setChessBoard(ChessCell chessBoard) {
        this.chessBoard = chessBoard;
    }

    public void setCurTeam(Team curTeam) {
        this.curTeam = curTeam;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void setIndexOfCurTeam(int indexOfCurTeam) {
        this.indexOfCurTeam = indexOfCurTeam;
    }

    public void setRoundsDone(int roundsDone) {
        this.roundsDone = roundsDone;
    }

    public void setChosenFigure(ChessFigure chosenFigure) {
        this.chosenFigure = chosenFigure;
    }

    public void setFigureCell(HashMap<ChessFigure, ChessCell> figureCell) {
        this.figureCell = figureCell;
    }

    public void setColorTeam(LinkedHashMap<ChessColour, Team> colorTeam) {
        this.colorTeam = colorTeam;
    }

    public void setPlayerTeam(LinkedHashMap<Player, Team> playerTeam) {
        this.playerTeam = playerTeam;
    }

    public void setFigurePlayer(HashMap<ChessFigure, Player> figurePlayer) {
        this.figurePlayer = figurePlayer;
    }

    public void setPlayerDirection(HashMap<Player, MoveDirection> playerDirection) {
        this.playerDirection = playerDirection;
    }

    public static void setColorDirection(HashMap<ChessColour, MoveDirection> colorDirection) {
        Game.colorDirection = colorDirection;
    }
}
