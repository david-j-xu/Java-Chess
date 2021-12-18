
public class Position {
    private int x;
    private int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isLegal() {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }
    
    @Override
    public String toString() {
        return this.getX() + " " + this.getY();
    }
    
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Position) {
            Position a = (Position) o;
            result = a.getY() == this.getY() && a.getX() == this.getX();
        }
        return result;
    }
    
    @Override
    public int hashCode() {
        return (x + "" + y).hashCode();
    }
    
    public Position move(int xDisp, int yDisp) {
        return new Position(x + xDisp, y + yDisp);
    }
}
