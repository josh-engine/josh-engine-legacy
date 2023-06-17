package co.josh.engine.render.joshshade.commands;

import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public interface JShaderCommand {
    int getArgsAmount();

    void setArgs(ArrayList<Object> args);

    void setInput(ArrayList<Object> in);

    JShaderCommand clone();

    ArrayList<Object> run();

    String functionName();
}
