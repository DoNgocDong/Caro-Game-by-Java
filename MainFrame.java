package RunGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private int row = 5;
    private int col = 5;
    private JButton newGame, undo;
    private Controller chessBoard;
    private JPanel mainPanel;
    private JLabel status;

    public MainFrame() {
        add(mainPanel = display());
        setTitle("Caro Game");
//        setResizable(false);
        setSize(1000, 725);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel display() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        chessBoard = new Controller(this, row, col);

        panel.add((creatMenu()), BorderLayout.NORTH);
        panel.add(chessBoard, BorderLayout.CENTER);

        return panel;
    }

    private JPanel creatMenu() {
        JPanel panel = new JPanel();
        status = new JLabel("X đánh trước");
        newGame = new JButton("New Game");
        undo = new JButton("Undo");

        status.setForeground(Color.RED);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialogNewGame("trận đấu chưa xong, bạn có chắc muốn game mới?",
                        "xác nhận", 0);
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessBoard.undo();
                undo.setEnabled(false);
            }
        });
        undo.setEnabled(false);

        panel.setLayout(new FlowLayout());
        panel.add(status);
        panel.add(newGame);
        panel.add(undo);

        return panel;
    }

    public void showDialogNewGame(String message, String title, int t) {
        int select = JOptionPane.showConfirmDialog(null, message, title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if(select == JOptionPane.YES_OPTION) {
            mainPanel.remove(chessBoard);
            chessBoard.removeAll();
            mainPanel.add(chessBoard = new Controller(this, row, col), BorderLayout.CENTER);
            mainPanel.validate();
            mainPanel.setVisible(true);
        }
        else{
            if(t == 1) {
                System.exit(0);
            }
        }
    }

    public JButton getUndo() {
        return this.undo;
    }
}
