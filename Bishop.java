import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Position init, int b) {
        super(init, b);
    }
    @Override
    public Move move(Position dest, Board b) {
        if (causesCheck(dest, b)) {
            return null;
        }
        Position start = this.getLocation();
        super.setLocation(dest);
        return new Move(PieceType.BISHOP, start, this.getLocation());
    }

    @Override
    public List<Position> getPossibleMoves(Board b) {
        Position next;
        Position curr = this.getLocation();
        Piece[][] board = b.getBoard();
        
        List<Position> toReturn = new ArrayList<Position>();
        
        int i = 1;
        while (curr.move(i, i).isLegal()) {
            next = curr.move(i, i);
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
        while (curr.move(-i, -i).isLegal()) {
            next = curr.move(-i, -i);
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
        while (curr.move(i, -i).isLegal()) {
            next = curr.move(i, -i);
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
        while (curr.move(-i, i).isLegal()) {
            next = curr.move(-i, i);
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
        return PieceType.BISHOP;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Bishop) {
            Bishop a = (Bishop) other;
            return this.getLocation().equals(a.getLocation());
        }
        return false;
    }
    
    public Piece copy() {
        return new Bishop(this.getLocation(), this.getTeam());
    }

}
