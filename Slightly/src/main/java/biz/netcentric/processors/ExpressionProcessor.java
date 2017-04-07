package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Node;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ExpressionProcessor {

    public Object process (String expression, ProcessingState state, OutputStreamWriter os) throws IOException, ScriptException {

        ScriptEngine engine = state.getEngine();
        try {
            return  engine.eval(expression);
        } catch (ScriptException e) {

            Map<String, Object> siblingState = state.getFromSiblingState(state.getSiblingIndex());

            Map<String, Object> existingObjects = new HashMap<>();
            if (siblingState != null) {
                for (Map.Entry<String, Object> entry : siblingState.entrySet()) {

                    if (engine.get(entry.getKey()) != null) {
                        existingObjects.put(entry.getKey(), engine.get(entry.getKey()));
                    }
                    engine.put(entry.getKey(), entry.getValue());

                }
            }

            Object result = engine.eval(expression);

            //restore objects
            for (Map.Entry<String, Object> entry : existingObjects.entrySet()) {
                engine.put(entry.getKey(), entry.getValue());

            }
            return result;
        }
            /*
            int currentIndex = state.getCurrentIndex();
            boolean found = false;
            int scope = currentIndex;

            while (!found && currentIndex <= scope && scope >=0) {

                Bindings bindings = state.getEngine().getBindings(scope);
                if (bindings != null) {

                    Map<String, Object> nodeMap = (Map<String, Object>) bindings.get(node.baseUri());
                    if (nodeMap != null) {
                        try {
                            return engine.eval(expression, bindings);
                        } catch (ScriptException ex) {


                            found = false;
                            scope --;
                        }
                    }
                }
                try {
                    return engine.eval(expression, bindings);
                } catch (ScriptException ex) {
                    found = false;
                    scope --;
                }
            }
            throw  e;
        }*/
    }
}
