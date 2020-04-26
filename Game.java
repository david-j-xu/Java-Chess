import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Game implements Runnable {
    
    private Desktop desktop;
    private static final JFrame FRAME = new JFrame("Chess");
    private Board board;
    
    public final static Map<Position, String> MAP = new HashMap<>();
    
    public static void main(String[] args) {
        char curr;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                curr = (char) (j + 97);
                MAP.put(new Position(i,j), "" + curr + (8 - i));
            }
        }
        SwingUtilities.invokeLater(new Game());
    }
    
    public void run() {
        FRAME.setLayout(new BorderLayout());
        board = new Board();
        FRAME.setIconImage(Board.rescale("WKing").getImage());
        desktop = Desktop.getDesktop();
        

        JPanel menu = this.makeMenu();
        
        JPanel status = board.getStatus();
        
        FRAME.add(menu, BorderLayout.NORTH);
        FRAME.add(board, BorderLayout.CENTER);
        FRAME.add(status, BorderLayout.SOUTH);

        FRAME.pack();
        FRAME.setVisible(true);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
    private JPanel makeMenu() {
        JPanel menu = new JPanel();
        JButton instructions = new JButton("Information");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FRAME, "This is a game of chess. \n"
                        + "Pieces are therefore moved according to the rules of chess.\n"
                        + "A link is in the \"Rules\" button if you don't know how to play. \n"
                        + "Pieces are moved by dragging them with the mouse and dropping\n"
                        + "All rules of chess have been implemented\n"
                        + "and I hope you have fun - David Xu\n");
            }
        });
        
        JButton rules = new JButton("Rules");
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URL("https://www.chess.com/learn-how-to-play-chess/")
                                            .toURI());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        
        JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        
        menu.setLayout(new GridLayout(1,0));
        menu.add(instructions);
        menu.add(rules);
        menu.add(reset);
        menu.add(undo);
        
        return menu;
    }
}
