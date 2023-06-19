package co.josh.engine.render.joshshade;

import co.josh.engine.render.joshshade.commands.*;
import co.josh.engine.render.joshshade.commands.data.input.CleanStreamCommand;
import co.josh.engine.render.joshshade.commands.data.input.DumpToStreamCommand;
import co.josh.engine.render.joshshade.commands.data.input.DumpWindowCommand;
import co.josh.engine.render.joshshade.commands.data.input.InsertCommand;
import co.josh.engine.render.joshshade.commands.data.modify.ModVertexCommand;
import co.josh.engine.render.joshshade.commands.data.modify.RecolorCommand;
import co.josh.engine.render.joshshade.commands.data.output.EndCommand;
import co.josh.engine.render.joshshade.commands.math.index.*;
import co.josh.engine.render.joshshade.commands.math.stat.*;
import co.josh.engine.util.exceptions.JoshShaderFailure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class JoshShaderLoader {
    static ArrayList<JShaderCommand> registeredCommands = new ArrayList<>();

    public static void init(){
        //1.0
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

        //1.1
        registeredCommands.add(new AddSCommand());
        registeredCommands.add(new MultiplySCommand());
        registeredCommands.add(new SubtractSCommand());
        registeredCommands.add(new SubtractRSCommand());
        registeredCommands.add(new DivideSCommand());
        registeredCommands.add(new DivideRSCommand());
        registeredCommands.add(new DumpWindowCommand());

    }

    public static void registerCommand(JShaderCommand command){
        registeredCommands.add(command);
    }

    public static String loadFile(String name){
        try{
            return Files.readString(Paths.get(name));
        } catch (IOException e) {
            throw new JoshShaderFailure("Could not find " + name);
        }
    }

    public static ArrayList<JShaderCommand> compile(String data){
        String[] lines = data.split("\\r?\\n");
        ArrayList<JShaderCommand> buf = new ArrayList<>();
        for (String line : lines){
            ArrayList<Object> split = new ArrayList<>(Arrays.asList(line.split(" ")));
            for (JShaderCommand rc : registeredCommands){
                if (Objects.equals(rc.functionName(), split.get(0))){
                    split.remove(0);
                    JShaderCommand newCommand = rc.clone();
                    newCommand.setArgs(split);
                    buf.add(newCommand);
                }
            }
        }
        return buf;
    }
}
