import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Status extends JPanel {
    private Board board;
    private int turn;
    
    public Status(Board b) {
        turn = b.getTurn();
        board = b;
        this.updateStatus();
    }
    
    public void updateStatus() {
        turn = board.getTurn();
        repaint();
    }
    
    public void paintComponent(Graphics gc) {
        turn = board.getTurn();
        
        gc.setColor(new Color(255, 255, 255));
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        gc.setColor(new Color(0, 0, 0));
        if (turn == 1) {
            gc.drawString("White to Move", (int) (board.getPreferredSize().getWidth() / 2), 20);
        } else {
            gc.drawString("Black to Move", (int) (board.getPreferredSize().getWidth() / 2), 20);
        }
    }
    
    public Dimension getPreferredSize() {
        Dimension size = board.getPreferredSize();
        return new Dimension((int) size.getWidth(), 30);
    }
}
