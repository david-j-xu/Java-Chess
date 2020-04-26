import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private boolean hasMoved;
    
    public Rook(Position position, int b) {
        super(position, b);
        hasMoved = false;
    }
    
    public Rook(Position position, int b, boolean hasMoved) {
        super(position, b);
        this.hasMoved = hasMoved;
    }

    @Override
    public Move move(Position dest, Board b) {
        if (causesCheck(dest, b)) {
            return null;
        }
        hasMoved = true;
        Position start = this.getLocation();
        super.setLocation(dest);
        return new Move(PieceType.ROOK, start, this.getLocation());
    }

    @Override
    public List<Position> getPossibleMoves(Board b) {
        Position next;
        Position curr = this.getLocation();
        Piece[][] board = b.getBoard();
        
        List<Position> toReturn = new ArrayList<Position>();
        
        int i = 1;
        while (curr.move(i, 0).isLegal()) {
            next = curr.move(i, 0);
            Piece currPiece = board[next.getY()][next.getX()];
            if (currPiece != null) {
                if (currPiece.getTeam() != this.getTeam()) {
                    toReturn.add(next);
                }
                break;
            } else {
                toReturn.add(next);
            }

            i++;
        }
        
        i = 1;
        while (curr.move(-i, 0).isLegal()) {
            next = curr.move(-i, 0);
            Piece currPiece = board[next.getY()][next.getX()];
            if (currPiece != null) {
                if (currPiece.getTeam() != this.getTeam()) {
                    toReturn.add(next);
                }
                break;
            } else {
                toReturn.add(next);
            }

            i++;
        }
        
        i = 1;
        while (curr.move(0, i).isLegal()) {
            next = curr.move(0, i);
            Piece currPiece = board[next.getY()][next.getX()];
            if (currPiece != null) {
                if (currPiece.getTeam() != this.getTeam()) {
                    toReturn.add(next);
                }
                break;
            } else {
                toReturn.add(next);
            }

            i++;
        }
        
        i = 1;
        while (curr.move(0, -i).isLegal()) {
            next = curr.move(0, -i);
            Piece currPiece = board[next.getY()][next.getX()];
            if (currPiece != null) {
                if (currPiece.getTeam() != this.getTeam()) {
                    toReturn.add(next);
                }
                break;
            } else {
                toReturn.add(next);
            }

            i++;
        }
        
        return toReturn;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }
    
    public void setHasMoved(boolean t) {
        hasMoved = t;
    }
    
    public boolean getHasMoved() {
        return hasMoved;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Rook) {
            Rook a = (Rook) other;
            return this.hasMoved == a.getHasMoved() &&
                        a.getLocation().equals(this.getLocation());
        }
        return false;
    }
    
    public Piece copy() {
        return new Rook(this.getLocation(), this.getTeam(), this.hasMoved);
    }
}
