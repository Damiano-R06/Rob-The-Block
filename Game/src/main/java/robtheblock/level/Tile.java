package robtheblock.level;

import robtheblock.objects.GameObject;
 
/**
 * represents a tile in the game level
 * tile can be either walkable or non-walkable
 */
public class Tile {
    private boolean isWalkable;
    private int tileType;
    private GameObject item;

    /**
     * constructor for tile 
     * @param isWalkable
     * @param tileType
     */
    public Tile(boolean isWalkable, int tileType) {
        this.isWalkable = isWalkable;
        this.tileType = tileType;
        this.item = null;
    }

    /**
     * checks if the tile is empty
     * 
     * @return true if empty
     */
    public boolean isEmpty() {
        return item == null;
    }

    /**
     * checks if tile has an item
     * @return true if it has an item
     */
    public boolean hasItem() {
        return item != null;
    }

    /**
     * returns item on the tile
     * 
     * @return item on the tile 
     */
    public GameObject getItem() {
        return item;
    }

    /**
    * places item on tile
    * 
    * @param item the gameobject to place on the tile
    */
    public void setItem(GameObject item) {
        this.item = item;
    }

    /**
     * removes item on tile
     */
    public void removeItem() {
        this.item = null;
    }

    /**
     * checks if tile is walkable
     * 
     * @return true if it is walkable
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * returbs the type ID of the tile
     * @return the tile type
     */
    public int getTileType() {
        return tileType;
    }
}

