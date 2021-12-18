import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Position init, int team) {
        super(init, team);
    }

    @Override
    public Move move(Position dest, Board b) {
        if (causesCheck(dest, b)) {
            return null;
        }
        Position start = this.getLocation();
        super.setLocation(dest);
        return new Move(PieceType.QUEEN, start, this.getLocation());
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
        
        i = 1;
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
        return PieceType.QUEEN;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Queen) {
            Queen a = (Queen) other;
            return this.getLocation().equals(a.getLocation());
        }
        return false;
    }
    
    public Piece copy() {
        return new Queen(this.getLocation(), this.getTeam());
    }

}
