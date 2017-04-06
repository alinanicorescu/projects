package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ExpressionProcessor {

    public Object process (String expression, ProcessingState state, OutputStreamWriter os) throws IOException, ScriptException {
        ScriptEngine engine = state.getEngine();
        Object result = engine.eval(expression);
        os.write("Expression processor :" + result + "\n");
        return result;
    }
}
