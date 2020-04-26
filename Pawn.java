import java.util.*;

import javax.swing.JOptionPane;

public class Pawn extends Piece {
    private boolean hasMoved;
    private boolean canEnPassant;
    private static final Object[] PROMOS = {"Queen", "Knight", "Rook", "Bishop"};
    
    public Pawn(Position init, int team) {
        super(init,team);
        hasMoved = false;
        canEnPassant = false;
    }
    
    public Pawn(Position init, int team, boolean hasMoved, boolean b) {
        super(init, team);
        this.hasMoved = hasMoved;
        canEnPassant = b;
    }
    
    @Override
    public Move move(Position dest, Board b) {

        if (b.getTurn() != this.getTeam()) {
            return null;
        }
        
        if (causesCheck(dest, b)) {
            return null;
        }
        
        Position init = this.getLocation();
        Position start = this.getLocation();

        super.setLocation(dest);
        hasMoved = true;
        
        if (dest.equals(start.move(-this.getTeam(), 1)) &&
                b.getLastMove().equals(new Move(PieceType.PAWN, 
                        start.move(-2 * this.getTeam(), 1), 
                        start.move(0, 1)))) {
            b.editBoard(dest.getX() + this.getTeam(), dest.getY(), null);
        } else if (dest.equals(start.move(-this.getTeam(), -1)) &&
                b.getLastMove().equals(new Move(PieceType.PAWN, 
                        start.move(-2 * this.getTeam(), -1), 
                        start.move(0, -1)))) {
            b.editBoard(dest.getX() + this.getTeam(), dest.getY(), null);
        }
        
        if ((this.getTeam() == 1 && dest.getX() == 0) ||
                (this.getTeam() == -1 && dest.getX() == 7)) {
            int n = JOptionPane.showOptionDialog(b, "What would you like to promote to?", 
                                                    "Promotion",
                                                    JOptionPane.YES_NO_OPTION, 
                                                    JOptionPane.QUESTION_MESSAGE, 
                                                    null, 
                                                    PROMOS, 
                                                    PROMOS[0]);
            Piece replacement = new Queen(dest, this.getTeam());
            if (n == 0) {
                replacement = new Queen(dest, this.getTeam());
            } else if (n == 1) {
                replacement = new Knight(dest, this.getTeam());
            } else if (n == 2) {
                replacement = new Rook(dest, this.getTeam());
                ((Rook) replacement).setHasMoved(true);
            } else if (n == 3) {
                replacement = new Bishop(dest, this.getTeam());
            }
            
            b.editBoard(dest.getX(), dest.getY(), replacement);
            return new Move(PieceType.PROMOTION, init, this.getLocation());
        }
        
        return new Move(PieceType.PAWN, init, this.getLocation());
    }

    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public List<Position> getPossibleMoves(Board b) {
        canEnPassant = false;
        
        List<Position> toReturn = new ArrayList<>();
        Piece[][] board = b.getPieces();
        Position next;
        if (!hasMoved) {
            next = this.getLocation().move(-2 * this.getTeam(), 0);
            if (next.isLegal() && board[next.getY()][next.getX()] == null &&
                    board[next.getY()][next.getX() + this.getTeam()] == null) {
                toReturn.add(next);
            }
        }
        
        
        next = this.getLocation().move(-this.getTeam(), 0);
        if (next.isLegal() && board[next.getY()][next.getX()] == null) {
            toReturn.add(next);
        }
        
        
        next = this.getLocation().move(-this.getTeam(), 1);
        if (next.isLegal() && board[next.getY()][next.getX()] != null &&
                board[next.getY()][next.getX()].getTeam() != this.getTeam()) {
            toReturn.add(next);
        } else if (next.isLegal() && board[next.getY()][next.getX()] == null) {
            if (!b.getMoves().isEmpty()) {
                Move lastMove = b.getLastMove();   
                if (lastMove.equals(new Move(PieceType.PAWN, 
                        this.getLocation().move(-2 * this.getTeam(), 1), 
                        this.getLocation().move(0, 1)))) {
                    toReturn.add(next);
                    canEnPassant = true;
                }
            }
        }
        
        next = this.getLocation().move(-this.getTeam(), -1);
        if (next.isLegal() && board[next.getY()][next.getX()] != null &&
                board[next.getY()][next.getX()].getTeam() != this.getTeam()) {
            toReturn.add(next);
        } else if (next.isLegal() && board[next.getY()][next.getX()] == null) {
            if (!b.getMoves().isEmpty()) {
                Move lastMove = b.getLastMove();
                if (lastMove.equals(new Move(PieceType.PAWN, 
                        this.getLocation().move(-2 * this.getTeam(), -1), 
                        this.getLocation().move(0, -1)))) {
                    toReturn.add(next);
                    canEnPassant = true;
                }
            }
        }
        
        
        
        return toReturn;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Pawn) {
            Pawn a = (Pawn) other;
            return this.hasMoved == a.getHasMoved() &&
                        this.getLocation().equals(a.getLocation()) &&
                        this.canEnPassant == a.getCanEnPassant();
        }
        return false;
    }

    public boolean getCanEnPassant() {
        return canEnPassant;
    }

    private boolean getHasMoved() {
        return hasMoved;
    }
    
    public Piece copy() {
        return new Pawn(this.getLocation(), this.getTeam(), this.hasMoved, this.canEnPassant);
    }
    
}
