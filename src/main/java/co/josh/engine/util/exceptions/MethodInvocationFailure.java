package co.josh.engine.util.exceptions;

import java.lang.reflect.Method;
import java.util.Set;

public class MethodInvocationFailure extends RuntimeException {
    public MethodInvocationFailure(Method method){
        super("MethodInvocationFailure: " + method.toString());
    }
    public MethodInvocationFailure(Set<Method> methods){
        super("MethodInvocationFailure: " + methods.toString());
    }

}
