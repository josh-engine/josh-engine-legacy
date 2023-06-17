package co.josh.engine.render.joshshade.commands;

import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public class CleanStreamCommand implements JShaderCommand {
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
        return new CleanStreamCommand();
    }

    public ArrayList<Object> run() {
        ArrayList<Object> buf = new ArrayList<>();
        for (Object o : input){
            if (o instanceof Vertex3F){
                buf.add(o);
                break;
            }
        }
        return input;
    }

    public String functionName() {
        return "cleanStream";
    }
}
