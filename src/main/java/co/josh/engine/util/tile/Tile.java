package co.josh.engine.util.tile;

import org.joml.Vector2i;

public class Tile {
    public int type;
    public String name;

    public Vector2i id;

    public Tile(int type, Vector2i id){
        this.type = type;
        this.name = "Tile";
        this.id = id;
    }

    public Tile(int type, String name, Vector2i id){
        this.type = type;
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }

        if (!(o instanceof Tile)){
            return false;
        }

        return (((Tile) o).type == this.type) && ((Tile) o).name.equals(this.name);
    }
}
