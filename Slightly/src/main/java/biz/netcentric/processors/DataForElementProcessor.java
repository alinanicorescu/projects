package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Element;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class DataForElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter outputStreamWriter) throws IOException, ScriptException {

        String dataForAttr = null;
        String varName = null;
        String dataForKey = null;

        Map<String, String> html5DataAttr = element.dataset();
        for (String key : html5DataAttr.keySet()) {
            if (key.startsWith("for")) {
                varName = key.substring("for-".length());
                dataForAttr = html5DataAttr.get(key);
                dataForKey = key;
                break;
            }
        }
                ExpressionProcessor expressionProcessor = new ExpressionProcessor();
                Object collection = expressionProcessor.process(dataForAttr, state, outputStreamWriter);
                Collection objects = (Collection)collection;
                state.initSiblingState();

                //put vars in context
                /*
                contextMap.put(varName, objects);
                Bindings bindings = state.getEngine().createBindings();
                bindings.put("siblingMap", contextMap);
                state.getEngine().setBindings(bindings, state.getCurrentIndex());
*/
                //changed dom
                element.removeAttr("data-" + dataForKey);
                int index = 1;
                for (Object obj : objects) {
                    element.after(element.html());
                    Map<String, Object> contextMap = new HashMap<>();
                    contextMap.put(varName, obj);
                    state.addToSiblingState(index++, contextMap);
                }
                System.out.println("data for: " + varName + " - -" + dataForAttr);
            }
        }



