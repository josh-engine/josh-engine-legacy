package co.josh.engine.util.tile.procedural.wfc;

import co.josh.engine.util.tile.Tile;
import co.josh.engine.util.tile.TileGrid;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;


public class WFCObject {
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<TileRelationship> relations = new ArrayList<>();

    public ArrayList<Tile> newArrayList(Tile t){
        ArrayList<Tile> a = new ArrayList<>();
        a.add(t);
        return a;
    }

    public void wfcParse(TileGrid example){
        ArrayList<Tile> trCenterIDs = new ArrayList<>();
        for (int yIter = 0; yIter <= (example.sizeY); yIter++){
            for (int xIter = 0; xIter <= (example.sizeX); xIter++){
                tiles.add(example.getTile(xIter,yIter));
                ArrayList<Tile>  up = newArrayList(example.getTile(xIter, yIter)),
                                 down = newArrayList(example.getTile(xIter, yIter)),
                                 left = newArrayList(example.getTile(xIter, yIter)),
                                 right = newArrayList(example.getTile(xIter, yIter));
                if (xIter > 1){
                    left = newArrayList(example.getTile(xIter - 1, yIter));
                }
                if (xIter < example.sizeX){
                    right = newArrayList(example.getTile(xIter + 1, yIter));
                }
                if (yIter > 1){
                    up = newArrayList(example.getTile(xIter, yIter - 1));
                }
                if (yIter < example.sizeY){
                    down = newArrayList(example.getTile(xIter, yIter + 1));
                }
                TileRelationship tr = new TileRelationship(example.getTile(xIter,yIter),
                                                            up, down, left, right);
                if (!trCenterIDs.contains(example.getTile(xIter,yIter))) {
                    relations.add(tr);
                    trCenterIDs.add(example.getTile(xIter,yIter));
                }
                else {
                    relations.get(trCenterIDs.indexOf(example.getTile(xIter,yIter))).up.addAll(up);
                    relations.get(trCenterIDs.indexOf(example.getTile(xIter,yIter))).down.addAll(down);
                    relations.get(trCenterIDs.indexOf(example.getTile(xIter,yIter))).left.addAll(left);
                    relations.get(trCenterIDs.indexOf(example.getTile(xIter,yIter))).right.addAll(right);
                }
            }
        }
    }

    public TileGrid wfcGen(int sizeX, int sizeY){
        SuperTileGrid superGrid = new SuperTileGrid(sizeX, sizeY, tiles);
        int curX = ThreadLocalRandom.current().nextInt(1, sizeX);
        int curY = ThreadLocalRandom.current().nextInt(1, sizeY);
        superGrid.getTile(curX, curY).possibilities = tiles;
        superGrid.getTile(curX, curY).collapse();
        superGrid.getTile(curX, curY).collapsed = new Tile(1, "Test", new Vector2i(curX, curY));
        //Once collapsed, apply filters to surrounding tiles.
        for (TileRelationship tr : relations){
            if (tr.thisTile.equals(superGrid.getTile(curX, curY).collapsed)){
                superGrid.getTile(curX, curY - 1).possibilities.retainAll(tr.up);
                superGrid.getTile(curX, curY + 1).possibilities.retainAll(tr.down);
                superGrid.getTile(curX - 1, curY).possibilities.retainAll(tr.right);
                superGrid.getTile(curX + 1, curY).possibilities.retainAll(tr.left);
            }
        }

        while (!superGrid.finished()){
            PriorityQueue<SuperTile> superGridSorted = new PriorityQueue<>(Comparator.comparingDouble(SuperTile::entropy));
            for (ArrayList<SuperTile> list : superGrid.l2d){
                superGridSorted.addAll(list);
            }
            superGridSorted.removeIf(st -> st.collapsed != null);
            SuperTile superTile = superGridSorted.remove();
            superGrid.getTile(superTile.id.x, superTile.id.y).collapse();
            //Once collapsed, apply filters to surrounding tiles.
            int x = superTile.id.x;
            int y = superTile.id.y;
            for (TileRelationship tr : relations){
                if (tr.thisTile.equals(superGrid.getTile(x, y).collapsed)){
                    superGrid.getTile(x, y - 1).possibilities.addAll(tr.up);
                    superGrid.getTile(x, y + 1).possibilities.addAll(tr.down);
                    superGrid.getTile(x - 1, y).possibilities.addAll(tr.right);
                    superGrid.getTile(x + 1, y).possibilities.addAll(tr.left);
                    superGrid.getTile(x, y - 1).possibilities.retainAll(tr.up);
                    superGrid.getTile(x, y + 1).possibilities.retainAll(tr.down);
                    superGrid.getTile(x - 1, y).possibilities.retainAll(tr.right);
                    superGrid.getTile(x + 1, y).possibilities.retainAll(tr.left);
                }
            }
            for (int yIter = 0; yIter <= (sizeY); yIter++){
                for (int xIter = 0; xIter <= (sizeX); xIter++){
                    if (superGrid.getTile(xIter, yIter).collapsed != null){
                        for (TileRelationship tr : relations){
                            if (tr.thisTile.equals(superGrid.getTile(xIter, yIter).collapsed)){
                                superGrid.getTile(xIter, yIter - 1).possibilities.retainAll(tr.up);
                                superGrid.getTile(xIter, yIter + 1).possibilities.retainAll(tr.down);
                                superGrid.getTile(xIter - 1, yIter).possibilities.retainAll(tr.right);
                                superGrid.getTile(xIter + 1, yIter).possibilities.retainAll(tr.left);
                            }
                        }
                    }
                }
            }
        }

        return superGrid.completed();
    }
}
