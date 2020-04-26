
public class Move {
    private PieceType type;
    private Position init;
    private Position dest;
    
    public Move(PieceType type, Position init, Position dest) {
        this.type = type;
        this.init = init;
        this.dest = dest;
    }

    public Position getInit() {
        return init;
    }

    public void setInit(Position init) {
        this.init = init;
    }

    public Position getDest() {
        return dest;
    }

    public void setDest(Position dest) {
        this.dest = dest;
    }
    
    public PieceType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        String moveType = "";
        if (type == PieceType.BISHOP) {
            moveType = "B";
        } else if (type == PieceType.KING) {
            moveType = "K";
        } else if (type == PieceType.QUEEN) {
            moveType = "Q";
        } else if (type == PieceType.ROOK) {
            moveType = "R";
        } else if (type == PieceType.KNIGHT) {
            moveType = "N";
        } 
        return moveType + Game.MAP.get(dest) 
            + " " + init.toString() 
            + " | " +  dest.toString() ;
    }
    
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Move) {
            Move a = (Move) o;
            result = (this.type == a.getType()) &&
                      this.dest.equals(a.getDest()) &&
                      this.init.equals(a.getInit());
        }
        return result;
    }
    
}
