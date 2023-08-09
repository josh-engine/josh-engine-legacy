package co.josh.engine.util.tile.procedural.wfc;

import co.josh.engine.util.tile.Tile;

import java.util.ArrayList;

public class TileRelationship{
    public ArrayList<Tile> up;

    public ArrayList<Tile> down;

    public ArrayList<Tile> left;

    public ArrayList<Tile> right;

    public Tile thisTile;

    public boolean diagonals;

    public ArrayList<Tile>  upleft;

    public ArrayList<Tile>  upright;

    public ArrayList<Tile>  downleft;

    public ArrayList<Tile>  downright;

    public TileRelationship(Tile thisTile, ArrayList<Tile>  up, ArrayList<Tile>  down, ArrayList<Tile>  left, ArrayList<Tile>  right){
        this.thisTile = thisTile;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.diagonals = false;
    }

    /*
    public TileRelationship(Tile thisTile, Tile up, Tile down, Tile left, Tile right, Tile upleft, Tile upright, Tile downleft, Tile downright){
        this.thisTile = thisTile;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.diagonals = true;
        this.upleft = upleft;
        this.upright = upright;
        this.downleft = downleft;
        this.downright = downright;
    }
     */

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TileRelationship)) return false;
        if (((TileRelationship) o).diagonals != diagonals) return false;
        if (!diagonals){
            return up.equals(((TileRelationship) o).up) &&
                    down.equals(((TileRelationship) o).down) &&
                    right.equals(((TileRelationship) o).right) &&
                    left.equals(((TileRelationship) o).left);
        }else{
            return up.equals(((TileRelationship) o).up) &&
                    down.equals(((TileRelationship) o).down) &&
                    right.equals(((TileRelationship) o).right) &&
                    left.equals(((TileRelationship) o).left) &&
                    upright.equals(((TileRelationship) o).upright) &&
                    upleft.equals(((TileRelationship) o).upleft) &&
                    downright.equals(((TileRelationship) o).downright) &&
                    downleft.equals(((TileRelationship) o).downleft);
        }
    }
}
