package co.josh.engine.util.tile.procedural.wfc;

import co.josh.engine.util.tile.Tile;
import co.josh.engine.util.tile.TileGrid;
import org.joml.Vector2i;

import java.util.ArrayList;

public class SuperTileGrid {
    public int sizeX;
    public int sizeY;

    public ArrayList<ArrayList<SuperTile>> l2d = new ArrayList<>();

    public SuperTileGrid(int sizeX, int sizeY, ArrayList<Tile> basePossibilities){
        super();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int y = 0; y < sizeY; y++){
            l2d.add(new ArrayList<>());
            for (int x = 0; x < sizeX; x++){
                l2d.get(y).add(new SuperTile(basePossibilities, new Vector2i(x, y)));
            }
        }
    }

    public SuperTile getTile(int x, int y){
        try{
            return l2d.get(y).get(x);
        } catch (Exception e){
            return new SuperTile(new ArrayList<>(), new Vector2i());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SuperTileGrid)) return false;
        return l2d.containsAll(((SuperTileGrid)o).l2d) && ((SuperTileGrid)o).l2d.containsAll(l2d);
    }

    public boolean finished() {
        boolean a = true;
        for (int y = 0; y < sizeY; y++){
            for (int x = 0; x < sizeX; x++) {
                a = a && (l2d.get(y).get(x).collapsed != null);
            }
        }
        return a;
    }

    public TileGrid completed(){
        if (!finished()){
            return null;
        }
        TileGrid returnMe = new TileGrid(this.sizeX, this.sizeY);
        for (int y = 0; y < sizeY; y++){
            for (int x = 0; x < sizeX; x++) {
                Tile t = l2d.get(y).get(x).collapsed;
                returnMe.setTile(x, y, t.type, t.name);
            }
        }
        return returnMe;
    }
}
