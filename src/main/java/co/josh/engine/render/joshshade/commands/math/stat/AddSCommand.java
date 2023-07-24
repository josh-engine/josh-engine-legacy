package co.josh.engine.render.joshshade.commands.math.stat;

import co.josh.engine.render.joshshade.commands.JShaderCommand;
import co.josh.engine.util.exceptions.JoshShaderFailure;

import java.util.ArrayList;
import java.util.Objects;

public class AddSCommand implements JShaderCommand {
    ArrayList<Object> input;

    Integer index;

    Float value;

    public int getArgsAmount() {
        return 2;
    }

    public void setArgs(ArrayList<Object> args) {
        Integer[] ints = findIndex(args);
        index = Objects.requireNonNull(ints)[0];
        int skipindex = ints[1];
        if (index == null) {
            throw new JoshShaderFailure("AddStatic: No index found!");
        }
        value = findValue(args, skipindex);
        if (value == null) {
            throw new JoshShaderFailure("AddStatic: No value!");
        }
    }

    private Integer[] findIndex(ArrayList<Object> args) {
        Integer i = 0;
        for (Object o : args) {
            if (o instanceof Integer) {
                return new Integer[]{(Integer) o, i};
            } else if (o instanceof String && !((String) o).startsWith("//")) {
                try {
                    return new Integer[]{Integer.parseInt((String) o), i};
                } catch (NumberFormatException ignored) {
                    System.out.println("(AddStatic) Warning: " + o + " is not a number!");
                }
            }
            i++;
        }
        return null;
    }

    private Float findValue(ArrayList<Object> args, int indexToSkip) {
        for (int i = 0; i < args.size(); i++) {
            if (i == indexToSkip) {
                continue;
            }

            Object o = args.get(i);

            if (o instanceof Float) {
                return (Float) o;
            } else if (o instanceof String && !((String) o).startsWith("//")) {
                try {
                    return Float.parseFloat((String) o);
                } catch (NumberFormatException ignored) {
                    System.out.println("(AddStatic) Warning: " + o + " is not a number!");
                }
            }
        }
        return null;
    }

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new AddSCommand();
    }

    public ArrayList<Object> run() {
        if (input.get(index) instanceof Number){
            Float a = Float.parseFloat(String.valueOf(input.get(index)));

            input.set(index, a+value);
        }
        return input;
    }

    public String functionName() {
        return "staticadd";
    }
}
