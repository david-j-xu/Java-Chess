import java.util.*;

public abstract class Piece {
    private Position location;
    private int team;
    
    public Piece(Position init, int team) {
        location = init;
        this.team = team;
    }
    
    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public abstract Move move(Position dest, Board board);
    public abstract List<Position> getPossibleMoves(Board board);
    public abstract PieceType getType();
    
    public int getTeam() {
        return team;
    }
    
    @Override
    public String toString() {
        return this.getType().toString() + " " + location.toString();
    }
    
    public boolean causesCheck(Position dest, Board b) {
        Piece[][] board = b.getBoard();
        Position kingLoc = b.getKing(this.getTeam()).getLocation();
        board[this.getLocation().getY()][this.getLocation().getX()] = null;
        board[dest.getY()][dest.getX()] = this;
        for (Piece[] v: board) {
            for (Piece u: v) {
                try {
                    if (u.getTeam() != this.getTeam() &&
                            u.getPossibleMoves(new Board(board, b)).contains(kingLoc)) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    public abstract Piece copy();
    
}
