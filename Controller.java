package RunGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller extends JPanel implements ActionListener {
    private int width = 800;
    private int height = 620;
    private int row, col;
    private int count = 1;
    private int size;
    private String chess;
    private int xUndo, yUndo;
    private JButton chessBoard[][];
    private boolean tick[][];
    private MainFrame frame;

    public Controller(MainFrame frame, int row, int col) {
        this.frame = frame;
        this.row = row;
        this.col = col;
        size = row * col;

//        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridLayout(row, col));
        setBorder(new EmptyBorder(1, 1, 1, 1));

        creatChessBoard();
    }

    private void creatChessBoard() {
        chessBoard = new JButton[row][col];
        tick = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                chessBoard[i][j] = creatButton(i + "," + j);
                chessBoard[i][j].setBackground(Color.white);
                tick[i][j] = false;
                add(chessBoard[i][j]);
            }
        }

    }

    private JButton creatButton(String action) {
        JButton btn = new JButton();
        btn.setActionCommand(action);
        btn.addActionListener(this);

        return btn;
    }

    private boolean checkPoint(int x, int y) {
        return tick[x][y];
    }

    private void addPoint(int x, int y) {
        if (count % 2 == 0)
            chess = "O";
        else
            chess = "X";

        chessBoard[x][y].setText(chess);
        count++;
        size--;
        System.out.println(size);

        xUndo = x;
        yUndo = y;

        frame.getUndo().setEnabled(true);
    }

    private void checkWin(int x, int y) {
        boolean check = isWinner(x, y);
        if(check)
            frame.showDialogNewGame(chess + " là người thắng!\nChơi trận mới?",
                                    "xác nhận", 1);
    }

    private boolean isWinner(int x, int y) {
        int d, i, j;
        //kiem tra hang ngang
        d=0; j=y;
        while(chessBoard[x][j].getText() == chess) {
            d++;
            j++;
        }
        j=y-1;
        while(chessBoard[x][j].getText() == chess) {
            d++;
            j--;
        }
        if(d > 4)
            return true;

        //kiem tra hang doc
        d=0; i=x;
        while(chessBoard[i][y].getText() == chess) {
            d++;
            i++;
        }
        i=x-1;
        while(chessBoard[i][y].getText() == chess) {
            d++;
            i--;
        }
        if(d > 4)
            return true;

        //kiem tra hang cheo 1
        d=0; i=x; j=y;
        while(chessBoard[i][j].getText() == chess) {
            d++;
            i--;
            j++;
        }
        i=x+1; j=y-1;
        while(chessBoard[i][j].getText() == chess) {
            d++;
            i++;
            j--;
        }
        if(d > 4)
            return true;

        //kiem tra hang cheo 2
        d=0; i=x; j=y;
        while(chessBoard[i][j].getText() == chess) {
            d++;
            i++;
            j++;
        }
        i=x-1; j=y-1;
        while(chessBoard[i][j].getText() == chess) {
            d++;
            i--;
            j--;
        }
        if(d > 4)
            return true;

        return false;
    }

    public void undo() {
        chessBoard[xUndo][yUndo].setText("");
        tick[xUndo][yUndo] = false;
        count--;
        size++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        int delimiter = str.indexOf(",");
        int x = Integer.parseInt(str.substring(0, delimiter));
        int y = Integer.parseInt(str.substring(delimiter + 1, str.length()));

        if (!checkPoint(x, y)) {
            try {
                addPoint(x, y);
                tick[x][y] = true;
                checkWin(x, y);
            }
            finally {
                if(size == 0) {
                    frame.showDialogNewGame("Hòa\nChơi trận mới?", "xác nhận", 1);
                }
            }
        }
        else
            System.out.println("the box is already checked");
    }
}
