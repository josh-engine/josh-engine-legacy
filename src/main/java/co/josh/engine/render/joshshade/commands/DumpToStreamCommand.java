package co.josh.engine.render.joshshade.commands;

import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public class DumpToStreamCommand implements JShaderCommand {
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
        return new DumpToStreamCommand();
    }

    public ArrayList<Object> run() {
        for (Object o : input){
            if (o instanceof Vertex3F){
                Vertex3F v = (Vertex3F) o;
                input.add(v.position.x);
                input.add(v.position.y);
                input.add(v.position.z);
                input.add(v.lastposition.x);
                input.add(v.lastposition.y);
                input.add(v.lastposition.z);
                input.add(v.color.x); //r
                input.add(v.color.y); //g
                input.add(v.color.z); //b
                input.add(v.color.w); //a
                input.add(v.texcoords.x);
                input.add(v.texcoords.y);
                break;
            }
        }
        return input;
    }

    public String functionName() {
        return "dumpVertex";
    }
}
