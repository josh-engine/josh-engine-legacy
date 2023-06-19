package co.josh.engine.render.joshshade.commands.data.input;

import co.josh.engine.Main;
import co.josh.engine.render.joshshade.commands.JShaderCommand;

import java.util.ArrayList;

public class DumpWindowCommand implements JShaderCommand {

    ArrayList<Object> input;

    public int getArgsAmount() {
        return 0;
    }

    public void setArgs(ArrayList<Object> args) {

    }

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new DumpWindowCommand();
    }

    public ArrayList<Object> run() {
        input.add(Main.width);
        input.add(Main.height);
        input.add(Main.currentWidth);
        input.add(Main.currentHeight);

        return input;
    }

    public String functionName() {
        return "dumpWindowInfo";
    }
}
