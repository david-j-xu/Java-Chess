import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    private static final int[][] DISP = new int[][] {new int[] {2, 1},
                                                     new int[] {2, -1},
                                                     new int[] {-2, 1},
                                                     new int[] {-2, -1},
                                                     new int[] {1, 2},
                                                     new int[] {1, -2},
                                                     new int[] {-1, 2},
                                                     new int[] {-1, -2}};

    public Knight(Position init, int team) {
        super(init, team);
    }

    @Override
    public Move move(Position dest, Board b) {
        if (causesCheck(dest, b)) {
            return null;
        }
        Position start = this.getLocation();
        super.setLocation(dest);
        return new Move(PieceType.KNIGHT, start, this.getLocation());
    }

    @Override
    public List<Position> getPossibleMoves(Board b) {
        Position next;
        Piece[][] board = b.getBoard();
        List<Position> toReturn = new ArrayList<Position>();
        
        for (int[] v: DISP) {
            int x = v[0];
            int y = v[1];
            next = this.getLocation().move(x, y);
            if (next.isLegal()) {
                if (board[next.getY()][next.getX()] != null &&
                        board[next.getY()][next.getX()].getTeam() != this.getTeam()) {
                    toReturn.add(next);
                } else if (board[next.getY()][next.getX()] == null) {
                    toReturn.add(next);
                }   
            }
        }        
        
        return toReturn;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Knight) {
            Knight a = (Knight) other;
            return this.getLocation().equals(a.getLocation());
        }
        return false;
    }

    public Piece copy() {
        return new Knight(this.getLocation(), this.getTeam());
    }
    
}
