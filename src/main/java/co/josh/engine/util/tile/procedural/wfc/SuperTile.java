package co.josh.engine.util.tile.procedural.wfc;

import co.josh.engine.util.tile.Tile;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SuperTile {
    public ArrayList<Tile> possibilities;

    public Tile collapsed;

    public Vector2i id;

    public double entropy(){
        ArrayList<Tile> allTiles = new ArrayList<>();
        ArrayList<Double> doubles = new ArrayList<>();
        //pass 1
        for (Tile p : possibilities){
            if (!allTiles.contains(p)){
                allTiles.add(p);
                doubles.add(0d);
            }
        }
        //pass 2
        for (Tile p : possibilities){
            for (Tile p1 : allTiles){
                if (p.equals(p1)){
                    doubles.set(allTiles.indexOf(p1), doubles.get(allTiles.indexOf(p1))+1f);
                }
            }
        }

        double e = 0;
        for (Double d : doubles){
            d = d/possibilities.size();
            e += d*Math.log(1/d);
        }
        return e;
    }

    public void collapse(){
        if (possibilities.isEmpty()){
            throw new RuntimeException("WFC Failure: No possibilities to collapse to!");
        } else{
            collapsed = possibilities.get(ThreadLocalRandom.current().nextInt(0, possibilities.size()));
        }
    }

    public SuperTile(ArrayList<Tile> possibilities, Vector2i index){
        this.possibilities = (ArrayList<Tile>) possibilities.clone();
        this.id = index;
    }
}
