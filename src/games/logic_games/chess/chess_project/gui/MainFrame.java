package games.logic_games.chess.chess_project.gui;

import games.logic_games.chess.chess_project.bot.GameWithBots;
import games.logic_games.chess.chess_project.enums.ChessColour;
import games.logic_games.chess.chess_project.chess.ChessCell;
import games.logic_games.chess.chess_project.chess.chessfigures.ChessFigure;
import games.logic_games.chess.chess_project.enums.MoveDirection;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;


public class MainFrame extends JFrame {
    private JButton buttonBack = new JButton("Назад");
    private JButton buttonNext = new JButton("Вперед");
    private JButton buttonPause = new JButton("Пауза");
    private JButton buttonPlay = new JButton("Пуск");
    private GameWithBots game;
    private ChessCellPaint curCell;
    private int chessCellSize = 40;
    private List<ChessCellPaint> chessCellPaintList = new ArrayList<>();
    private Timer timer = new Timer(1000, e -> {
        if (!game.isBotToPlay()) return;
        actionPaint();
        repaint();
    });

   /* private Thread thread1;
    private Thread thread2;

    private class run1 implements Runnable {
        public void run() {
            game.botChoose();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            System.out.println("choose");
            repaint();
        }
    }
    private class run2 implements Runnable {
        public void run() {
            game.botMoveFigure();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            System.out.println("move");
            repaint();
        }
    }*/
    private void actionPaint() {
        if (game.isBotToPlay()) {
            game.botChoose();
            game.botMoveFigure();
        }
    }


    public MainFrame(GameWithBots game) {
        this.game = game;
        setLayout(null);
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setPaintCells();

        addButtons();

        timer.start();
    }
    private void addButtons() {
        buttonBack.setBounds(800, 50, 100, 70);
        buttonBack.setVisible(true);
        buttonBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();

                game.stepBack();

                MainFrame.this.repaint();

                //timer.start();
            }
        });
        add(buttonBack);

        buttonNext.setBounds(800, 130, 100, 70);
        buttonNext.setVisible(true);
        buttonNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();

                game.stepFromSaved();

                MainFrame.this.repaint();

                //timer.start();
            }
        });
        add(buttonNext);

        buttonPause.setBounds(800, 210, 100, 70);
        buttonPause.setVisible(true);
        buttonPause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
            }
        });
        add(buttonPause);

        buttonPlay.setBounds(800, 290, 100, 70);
        buttonPlay.setVisible(true);
        buttonPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.start();
            }
        });
        add(buttonPlay);
    }

    private void setPaintCells()  {
        int xStart = 250;
        int yStart = 250;

        makePaintCells(xStart, yStart, game.getChessBoard());
    }

    private void makePaintCells (int x, int y, ChessCell chessCell) {
        if (chessCell == null) {
            return;
        }
        ChessCellPaint paintCell = new ChessCellPaint(x, y, chessCell);
        if (containsPaintCell(paintCell)) return;

        chessCellPaintList.add(paintCell);
        add(paintCell);

        List<MoveDirection> directions = List.of(MoveDirection.UP, MoveDirection.DOWN,
                MoveDirection.LEFT, MoveDirection.RIGHT);
        HashMap<MoveDirection, Integer> moveX = new HashMap<>();
        moveX.put(MoveDirection.RIGHT, chessCellSize);
        moveX.put(MoveDirection.LEFT, -chessCellSize);
        moveX.put(MoveDirection.DOWN, 0);
        moveX.put(MoveDirection.UP, 0);
        HashMap<MoveDirection, Integer> moveY = new HashMap<>();
        moveY.put(MoveDirection.RIGHT, 0);
        moveY.put(MoveDirection.LEFT, 0);
        moveY.put(MoveDirection.DOWN, chessCellSize);
        moveY.put(MoveDirection.UP, -chessCellSize);

        for (MoveDirection direction: directions) {
            makePaintCells(x + moveX.get(direction), y + moveY.get(direction),
                    chessCell.getCellByDirection(direction));
        }
    }
    private boolean containsPaintCell (ChessCellPaint chessCellPaint) {
        for (ChessCellPaint cell: chessCellPaintList) {
            if (cell.equals(chessCellPaint)) return true;
        }

        return false;
    }

    private class ChessCellPaint extends JComponent {
        int xStart;
        int yStart;
        public ChessCell cell;

        public ChessCellPaint(int xStart, int yStart, ChessCell cell) {
            this.xStart = xStart;
            this.yStart = yStart;
            this.cell = cell;
            setLocation(this.xStart, this.yStart);
            setSize(chessCellSize, chessCellSize);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //actionPaint();
                    //if (game.isBotToPlay()) return;

                    curCell = (ChessCellPaint) e.getSource();

                    boolean hasMoved = game.moveChosenFigureTo(curCell.cell);
                    if (!hasMoved) {
                        game.chooseFigure(curCell.cell.getFigureOn());
                    } else {
                        game.chooseFigure(null);
                    }

                    MainFrame.this.repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(ChessColourToColor(cell.getColor()));

            ChessFigure figure = this.cell.getFigureOn();

            List<ChessCell> whereCanGo = game.whereCanChosenGo();

            List<MoveDirection> borders = new LinkedList<>(Arrays.asList(MoveDirection.RIGHT,
                    MoveDirection.UP, MoveDirection.DOWN, MoveDirection.LEFT));
            for (MoveDirection direction: cell.getMap().keySet()) {
                if (cell.getCellByDirection(direction) != null) {
                    borders.remove(direction);
                }
            }
            if (whereCanGo != null) {
                if (whereCanGo.contains(this.cell)) {
                    ChessDrawer.drawToBeatCell(g2, ChessColourToColor(cell.getColor()), getWidth(), getHeight());
                } else {
                    ChessDrawer.drawCell(g2, ChessColourToColor(cell.getColor()), getWidth(), getHeight(), borders);
                }
            } else {
                ChessDrawer.drawCell(g2, ChessColourToColor(cell.getColor()), getWidth(), getHeight(), borders);
            }

            if (figure == null) return;
            if (game.getChosenFigure() == figure) ChessDrawer.drawToBeatCell(g2, Color.GREEN, getWidth(), getHeight());
            ChessDrawer.drawFigure(figure, g2);
        }

        private Color ChessColourToColor(ChessColour chessColour) {
            if (chessColour == ChessColour.BLACK) return new Color(160,64,0);

            return new Color(224,192,128);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ChessCellPaint)) return false;

            return ((ChessCellPaint)o).xStart == xStart && ((ChessCellPaint)o).yStart == yStart &&
                    ((ChessCellPaint)o).cell == cell;
        }
    }

}
