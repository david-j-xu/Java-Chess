
public class BoardState {
    private Piece[][] board;
    
    public BoardState(Piece[][] board) {
        this.board = board;
    }
    
    public Piece[][] getBoard() {
        Piece[][] toReturn = new Piece[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    toReturn[i][j] = board[i][j].copy();
                }
            }
        }
        return toReturn;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof BoardState) {
            BoardState a = (BoardState) other;
            Piece[][] c = a.getBoard();
            for (int i = 0; i < c.length; i++) {
                for (int j = 0; j < c[i].length; j++) {
                    Piece curr = c[i][j];
                    Piece curr1 = board[i][j];
                    if (curr == null ^ curr1 == null) {
                        return false;
                    } else if (curr != null && curr1 != null) {
                        if (!curr.equals(curr1)) {
                            return false;
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
