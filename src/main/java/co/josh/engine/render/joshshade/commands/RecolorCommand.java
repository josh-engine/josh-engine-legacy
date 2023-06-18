package co.josh.engine.render.joshshade.commands;

import co.josh.engine.util.exceptions.JoshShaderFailure;
import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public class RecolorCommand implements JShaderCommand {
    ArrayList<Object> input;

    ArrayList<Float> arguments;

    public int getArgsAmount() {
        return 4;
    }

    public void setArgs(ArrayList<Object> args) {
        ArrayList<Float> _args = new ArrayList<>();
        for (Object o : args){
            if (!(o instanceof Float)){
                if (o instanceof String){
                    if (((String) o).startsWith("//")){
                        break;
                    }
                    try {
                        o = Float.parseFloat((String) o);
                        _args.add((Float) o);
                        continue;
                    } catch (Exception ignored) {

                    }
                }
                System.out.println("(Recolor) Warning: " + o + " is not a number!");
            } else {
                _args.add((Float) o);
            }
        }
        arguments = _args;
        if (arguments.size() != 4){
            throw new JoshShaderFailure("Recolor: Not enough args!");
        }
    }

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new RecolorCommand();
    }

    public ArrayList<Object> run() {
        if (input.get(0) instanceof Vertex3F){
            input.set(0,((Vertex3F) input.get(0)).col(
                    arguments.get(0),
                    arguments.get(1),
                    arguments.get(2),
                    arguments.get(3)));
        }
        return input;
    }

    public String functionName() {
        return "recolor";
    }
}
