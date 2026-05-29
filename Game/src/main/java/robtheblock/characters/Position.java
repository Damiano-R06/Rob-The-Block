package robtheblock.characters;

/**
 * Represents a 2D grid position with X and Y coordinates.
 */ 
public class Position {
    private int x;
    private int y;
    
    /**
     * creates a postion with x and y coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns x coordinate
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns y coordinate
     * @return return y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * sets x coordinate
     * @param x new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * sets y coordinate
     * @param y new y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * return postion as a string in a x and y format
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


   
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }
}