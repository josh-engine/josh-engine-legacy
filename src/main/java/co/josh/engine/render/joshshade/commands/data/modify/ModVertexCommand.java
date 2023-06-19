package co.josh.engine.render.joshshade.commands.data.modify;

import co.josh.engine.render.joshshade.commands.JShaderCommand;
import co.josh.engine.util.exceptions.JoshShaderFailure;
import co.josh.engine.util.render.Vertex3F;

import java.util.ArrayList;

public class ModVertexCommand implements JShaderCommand {
    ArrayList<Object> input;

    Integer index = null;

    Integer index2 = null;


    Float value = null;

    public int getArgsAmount() {
        return 2;
    }

    public void setArgs(ArrayList<Object> args) {
        ArrayList<Integer> _args = new ArrayList<>();
        for (Object o : args){
            if (!(o instanceof Integer)){
                if (o instanceof String){
                    if (((String) o).startsWith("//")){
                        break;
                    }
                    try {
                        o = Integer.parseInt((String) o);
                        _args.add((Integer) o);
                        continue;
                    } catch (Exception ignored) {

                    }
                }
                System.out.println("(ModifyVertex) Warning: " + o + " is not a number!");
            } else {
                _args.add((Integer) o);
            }
        }
        index = _args.get(0);
        index2 = _args.get(1);
        if (index2 == null || index == null){
            throw new JoshShaderFailure("ModifyVertex: Wrong number of args!");
        }
    }

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new ModVertexCommand();
    }

    public ArrayList<Object> run() {
        if (input.get(index2) instanceof Float){
            value = (Float) input.get(index2);
        } else {
            throw new JoshShaderFailure("ModifyVertex: index given is not a float!");
        }
        Vertex3F vert = null;
        int i = 0;
        for (Object o : input){
            if (o instanceof Vertex3F){
                vert = (Vertex3F) o;
                break;
            }
            i++;
        }
        if (vert == null){
            throw new JoshShaderFailure("ModifyVertex: No Vertex3F object found in dataStream!");
        }
        try{
            Float[] floats = vert.dump();
            floats[index] = value;
            input.set(i, Vertex3F.pack(floats));
        } catch (Exception e) {
            throw new JoshShaderFailure("ModifyVertex: " + e);
        }
        return input;
    }

    public String functionName() {
        return "modVertex";
    }
}
