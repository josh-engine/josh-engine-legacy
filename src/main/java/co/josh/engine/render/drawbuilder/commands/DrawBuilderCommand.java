package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;

public interface DrawBuilderCommand {
    void run(int GL_MODE, int i, ShadersObject shaders, float t);
}
