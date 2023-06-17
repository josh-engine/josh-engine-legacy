package co.josh.engine.render.joshshade.commands;

import co.josh.engine.util.exceptions.JoshShaderFailure;
import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public class EndCommand implements JShaderCommand {
    ArrayList<Object> input;

    public int getArgsAmount() {
        return 0;
    }

    public void setArgs(ArrayList<Object> args) {}

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new EndCommand();
    }

    public ArrayList<Object> run() {
        return input;
    }

    public String functionName() {
        return "out";
    }

    public Vertex3F finalVert(){
        for (Object o : input){
            if (o instanceof Vertex3F){
                return (Vertex3F) o;
            }
        }
        throw new JoshShaderFailure("End: No Vertex3F in data stream!");
    }
}
