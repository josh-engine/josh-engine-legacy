package co.josh.engine.util.tile;

import org.joml.Vector2i;

import java.util.ArrayList;

public class TileGrid {
    public int sizeX;
    public int sizeY;

    public ArrayList<ArrayList<Tile>> l2d = new ArrayList<>();

    public TileGrid(int sizeX, int sizeY){
        super();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int y = 0; y < sizeY; y++){
            l2d.add(new ArrayList<>());
            for (int x = 0; x < sizeX; x++){
                l2d.get(y).add(new Tile(0, new Vector2i(x, y)));
            }
        }
    }
    public void setTile(int x, int y, int type, String name){
        l2d.get(y).set(x, new Tile(type, name, new Vector2i(x, y)));
    }

    public Tile getTile(int x, int y){
        try{
            return l2d.get(y).get(x);
        } catch (Exception e){
            return new Tile(0, new Vector2i());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TileGrid)) return false;
        return l2d.containsAll(((TileGrid)o).l2d) && ((TileGrid)o).l2d.containsAll(l2d);
    }

    @Override
    public Object clone(){
        ArrayList<ArrayList<Tile>> a = (ArrayList<ArrayList<Tile>>) l2d.clone();
        TileGrid t = new TileGrid(sizeX, sizeY);
        t.l2d = a;
        return t;
    }

}
