package co.josh.engine.render.joshshade.commands.math;

import co.josh.engine.render.joshshade.commands.JShaderCommand;
import co.josh.engine.util.exceptions.JoshShaderFailure;

import java.util.ArrayList;

public class DivideCommand implements JShaderCommand {
    ArrayList<Object> input;
    ArrayList<Integer> arguments;

    public int getArgsAmount() {
        return 2;
    }

    public void setArgs(ArrayList<Object> args) {
        ArrayList<Integer> _args = new ArrayList<>();
        for (Object o : args){
            if (!(o instanceof Integer)){
                if (o instanceof String){
                    if (((String) o).equals("//")){
                        break;
                    }
                    try {
                        o = Integer.parseInt((String) o);
                        _args.add((Integer) o);
                        continue;
                    } catch (Exception ignored) {

                    }
                }
                System.out.println("(Divide) Warning: " + o + " is not a number!");
            } else {
                _args.add((Integer) o);
            }
        }
        arguments = _args;
        if (arguments.size() != 2){
            throw new JoshShaderFailure("Divide: Wrong number of args!");
        }
    }

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new DivideCommand();
    }

    public ArrayList<Object> run() {
        ArrayList<Object> buf = new ArrayList<>();
        if (input.get(arguments.get(0)) instanceof Number && input.get(arguments.get(1)) instanceof Number){
            input.set(arguments.get(0), (Float)input.get(arguments.get(0))/(Float)input.get(arguments.get(1)));
        }
        return input;
    }

    public String functionName() {
        return "divide";
    }
}
