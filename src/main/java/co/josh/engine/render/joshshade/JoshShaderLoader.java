package co.josh.engine.render.joshshade;

import co.josh.engine.render.joshshade.commands.*;
import co.josh.engine.render.joshshade.commands.math.*;
import co.josh.engine.util.exceptions.JoshShaderFailure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class JoshShaderLoader {
    public static ArrayList<JShaderCommand> registeredCommands = new ArrayList<>();

    public static void init(){
        registeredCommands.add(new CleanStreamCommand());
        registeredCommands.add(new RecolorCommand());
        registeredCommands.add(new EndCommand());
        registeredCommands.add(new DumpToStreamCommand());
        registeredCommands.add(new InsertCommand());
        registeredCommands.add(new AddCommand());
        registeredCommands.add(new MultiplyCommand());
        registeredCommands.add(new SubtractCommand());
        registeredCommands.add(new SubtractReverseCommand());
        registeredCommands.add(new DivideCommand());
        registeredCommands.add(new DivideReverseCommand());
        registeredCommands.add(new ModVertexCommand());

    }

    public static void registerCommand(JShaderCommand command){
        registeredCommands.add(command);
    }

    public static String loadFile(String name){
        try{
        String text = Files.readString(Paths.get(name));
        return text;
        } catch (IOException e) {
            throw new JoshShaderFailure("Could not find " + name);
        }
    }

    public static ArrayList<JShaderCommand> compile(String data){
        String[] lines = data.split("\\r?\\n");
        ArrayList<JShaderCommand> buf = new ArrayList<>();
        for (String line : lines){
            ArrayList<Object> split = new ArrayList<>();
            split.addAll(Arrays.asList(line.split(" ")));
            for (JShaderCommand rc : registeredCommands){
                if (Objects.equals(rc.functionName(), split.get(0))){
                    JShaderCommand newCommand = rc.clone();
                    split.remove(0);
                    newCommand.setArgs(split);
                    buf.add(newCommand);
                }
            }
        }
        return buf;
    }
}
