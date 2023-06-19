package co.josh.engine.render.joshshade;

import co.josh.engine.Main;
import co.josh.engine.render.joshshade.commands.data.output.EndCommand;
import co.josh.engine.render.joshshade.commands.JShaderCommand;
import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public class JShader {
    public ArrayList<JShaderCommand> commands;

    public JShader(String fileName){
        commands = JoshShaderLoader.compile(JoshShaderLoader.loadFile(Main.dir + fileName));
    }

    public Vertex3F shade(Vertex3F input, ArrayList<Object> other_data){
        ArrayList<Object> dataStream = new ArrayList<>();
        ArrayList<Object> tempData = new ArrayList<>();
        Vertex3F output = null;
        dataStream.add(input);
        dataStream.addAll(other_data);
        for (JShaderCommand command : commands){
            tempData.addAll(dataStream); // It modified itself weirdly while running. Workaround
            command.setInput(tempData);
            dataStream = command.run();
            tempData = new ArrayList<>();
            if (command instanceof EndCommand){
                output = ((EndCommand) command).finalVert();
                break;
            }
        }
        return output;
    }
}
