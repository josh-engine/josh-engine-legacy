package co.josh.engine.util.tile.draw;


import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.render.drawbuilder.commands.DrawBuilderCommand;
import co.josh.engine.util.Transform;
import co.josh.engine.util.model.ModelReader;
import co.josh.engine.util.model.jmodel.JoshModel;
import co.josh.engine.util.render.Vertex3F;
import co.josh.engine.util.tile.TileGrid;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.HashMap;

public class TileGridRendererComponent implements Component {

    public GameObject parent;

    public TileGrid tileGrid;

    TileGrid last;

    public float tileSize;

    public ArrayList<DrawBuilderCommand> dbcs = new ArrayList<>();

    public TileGridRendererComponent(GameObject parent, TileGrid tileGrid, float tileSize){
        this.parent = parent;
        this.tileGrid = tileGrid;
        this.tileSize = tileSize;
        this.last = tileGrid;
        quadVerts.add(new Vertex3F(
                new Vector3f(-tileSize/2f, -tileSize/2f, 0f), new Vector4f(1f, 1f, 1f, 1f)));
        quadVerts.add(new Vertex3F(
                new Vector3f(-tileSize/2f, tileSize/2f, 0f), new Vector4f(1f, 1f, 1f, 1f)));
        quadVerts.add(new Vertex3F(
                new Vector3f(tileSize/2f, tileSize/2f, 0f), new Vector4f(1f, 1f, 1f, 1f)));
        quadVerts.add(new Vertex3F(
                new Vector3f(tileSize/2f, -tileSize/2f, 0f), new Vector4f(1f, 1f, 1f, 1f)));
        colorData.put(0, new Vector4f(0f, 0f, 0f, 0f));
        colorData.put(1, new Vector4f(1f, 0f, 0f, 1f));
        colorData.put(2, new Vector4f(0f, 1f, 0f, 1f));
        colorData.put(3, new Vector4f(0f, 0f, 1f, 1f));
        updateTileGrid();
    }

    public String getName() {
        return "TileGridRenderer";
    }

    ArrayList<Vertex3F> quadVerts = new ArrayList<>();

    HashMap<Integer, Vector4f> colorData = new HashMap<>();

    JoshModel tile = ModelReader.loadJoshFormat(Main.dir + Main.gameFolder + "/models/tile.josh", false);

    void updateTileGrid(){
        dbcs.clear();
        Transform t = new Transform(new Vector3f(tileSize, tileSize, 0f).add(parent.getTransform().position));
        t.scale = new Vector3f(tileSize*tileGrid.sizeX, tileSize*tileGrid.sizeY, 1f);
        dbcs.addAll(tile.drawBuilderCommands(t, t, new Vector4f(0f, 0f, 0f, 1f)));

        for (int yIter = 0; yIter < tileGrid.sizeY; yIter++){
            for (int xIter = 0; xIter < tileGrid.sizeX; xIter++)
            {
                if (tileGrid.getTile(xIter, yIter).type != 0) {
                    t = new Transform(new Vector3f((xIter + 1) * tileSize, (yIter + 1) * tileSize, 0f));
                    t.position.add(parent.getTransform().position);
                    t.scale = new Vector3f(tileSize);
                    Vector4f c = colorData.get(tileGrid.getTile(xIter, yIter).type);
                    dbcs.addAll(tile.drawBuilderCommands(t, t, c));
                }
            }
        }
        last = (TileGrid) tileGrid.clone();
    }

    public void on2D() {
        updateTileGrid();
        DrawBuilder db = new DrawBuilder(Main.camera, GL13.GL_QUADS);
        db.render(dbcs, (float) Main.fpsCount / Main.fps);
    }
    public GameObject getParent() {
        return null;

    }
}
