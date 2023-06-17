package co.josh.engine.render.joshshade.commands;

import co.josh.engine.util.exceptions.JoshShaderFailure;

import java.util.ArrayList;

public class InsertCommand implements JShaderCommand {
    ArrayList<Object> input;

    Integer index;

    Float value;

    public int getArgsAmount() {
        return 2;
    }

    public void setArgs(ArrayList<Object> args) {
        int i = 0;
        for (Object o : args){
            if (!(o instanceof Integer)){
                if (o instanceof String){
                    if (((String) o).equals("//")){
                        break;
                    }
                    try {
                        o = Integer.parseInt((String) o);
                        index = (Integer) o;
                        break;
                    } catch (Exception ignored) {

                    }
                }
                System.out.println("(Insert) Warning: " + o + " is not a number!");
            } else {
                index = (Integer) o;
            }
            i++;
        }
        int index_of_arg1 = i;
        i = 0;
        for (Object o : args){
            if (index_of_arg1 == i){
                continue;
            }
            if (!(o instanceof Float)){
                if (o instanceof String){
                    if (((String) o).equals("//")){
                        break;
                    }
                    try {
                        o = Float.parseFloat((String) o);
                        value = (Float) o;
                        break;
                    } catch (Exception ignored) {

                    }
                }
                System.out.println("(Insert) Warning: " + o + " is not a number!");
            } else {
                value = (Float) o;
            }
            i++;
        }
        if (value == null || index == null){
            throw new JoshShaderFailure("Insert: Wrong number of args!");
        }
    }

    public void setInput(ArrayList<Object> in) {
        this.input = in;
    }

    public JShaderCommand clone() {
        return new InsertCommand();
    }

    public ArrayList<Object> run() {
        ArrayList<Object> buf = new ArrayList<>();
        buf.add(index, value);
        return input;
    }

    public String functionName() {
        return "insert";
    }
}
