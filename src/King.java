import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private boolean hasMoved;
    
    public King(Position init, int team) {
        super(init, team);
        hasMoved = false;
    }
    
    public King(Position init, int team, boolean hasMoved) {
        super(init, team);
        this.hasMoved = hasMoved;
    }

    @Override
    public Move move(Position dest, Board b) {
        int x = this.getLocation().getX();
        if (causesCheck(dest, b)) {
            return null;
        } else if (dest.equals(new Position(x, 6)) && !hasMoved) {
            if (causesCheck(dest, b) || 
                   causesCheck(new Position(x, 5), b) ||
                   causesCheck(new Position(x, 4), b)) {
                return null;
            } else {
                hasMoved = true;
                Position start = this.getLocation();
                super.setLocation(dest);
                Piece kRook = b.getBoard()[7][x];
                ((Rook) kRook).setHasMoved(true);
                kRook.setLocation(new Position(x, 5));
                b.editBoard(x, 7, null);
                b.editBoard(x, 5, kRook);
                return new Move(PieceType.KING, start, this.getLocation());
            }
            
        } else if (dest.equals(new Position(this.getLocation().getX(), 2)) && !hasMoved) {
            if (causesCheck(dest, b) || 
                    causesCheck(new Position(x, 4), b) ||
                    causesCheck(new Position(x, 3), b) ||
                    causesCheck(new Position(x, 2), b)) {
                return null;
            } else {
                hasMoved = true;
                Position start = this.getLocation();
                super.setLocation(dest);
                Piece qRook = b.getBoard()[0][x];
                ((Rook) qRook).setHasMoved(true);
                qRook.setLocation(new Position(x, 3));
                b.editBoard(x, 0, null);
                b.editBoard(x, 3, qRook);
                return new Move(PieceType.KING, start, this.getLocation());
            }

        }
        hasMoved = true;
        Position start = this.getLocation();
        super.setLocation(dest);
        return new Move(PieceType.KING, start, this.getLocation());
    }

    @Override
    public List<Position> getPossibleMoves(Board b) {
        List<Position> toReturn = new ArrayList<Position>();
        Position curr = this.getLocation();
        Piece[][] board = b.getBoard();
        Position next;
        
        for (int i = curr.getX() - 1; i < curr.getX() + 2; i++) {
            for (int j = curr.getY() - 1; j < curr.getY() + 2; j++) {
                if (i != curr.getX() || j != curr.getY()) {
                    next = new Position(i, j);
                    if (next.isLegal()) {
                        if (board[next.getY()][next.getX()] != null) {
                            if (board[next.getY()][next.getX()].getTeam() != this.getTeam()) {
                                toReturn.add(next);
                            }
                        } else {
                            toReturn.add(next);
                        }
                    }
                }
            }
        }
        
        int y = this.getLocation().getX();

        if (canShortCastle(board)) {
            toReturn.add(new Position(y, 6));
        }
        
        if (canLongCastle(board)) {
            toReturn.add(new Position(y, 2));
        }
              
        return toReturn;
    }

    @Override
    public boolean causesCheck(Position dest, Board b) {
        Piece[][] board = b.getBoard();
        board[this.getLocation().getY()][this.getLocation().getX()] = null;
        board[dest.getY()][dest.getX()] = this;
        for (Piece[] v: board) {
            for (Piece u: v) {
                try {
                    if (u.getTeam() != this.getTeam() &&
                            u.getPossibleMoves(new Board(board, b)).contains(dest)) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }
    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
    
    public boolean canShortCastle(Piece[][] board) {
        int y = this.getLocation().getX();
        Piece kRook = board[7][y];
        if (!hasMoved && kRook != null &&
                kRook.getType() == PieceType.ROOK &&
                !((Rook) kRook).getHasMoved() && 
                board[5][y] == null && 
                board[6][y] == null) {
            return true;
        }
        return false;
    }
    
    public boolean canLongCastle(Piece[][] board) {
        int y = this.getLocation().getX();
        Piece qRook = board[0][y];
        if (!hasMoved && qRook != null &&
                qRook.getType() == PieceType.ROOK &&
                !((Rook) qRook).getHasMoved() && 
                board[1][y] == null && 
                board[2][y] == null &&
                board[3][y] == null) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof King) {
            King a = (King) other;
            return (this.getLocation().equals(a.getLocation()) &&
                        this.hasMoved == a.getHasMoved());
        }
        
        return false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
    
    public Piece copy() {
        return new King(this.getLocation(), this.getTeam(), this.getHasMoved());
    }

}
