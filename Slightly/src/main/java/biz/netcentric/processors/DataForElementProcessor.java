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
        String keyName = null;
        Map<String, String> html5DataAttr = element.dataset();
        for (String key : html5DataAttr.keySet()) {
            if (key.startsWith("for")) {
                varName = key.substring("for-".length());
                dataForAttr = html5DataAttr.get(key);
                keyName = "data-" + key;
                break;
            }
        }
        if (varName != null) {
            if (state.getForDataIterator() == null) {
                ExpressionProcessor expressionProcessor = new ExpressionProcessor();
                Object collection = expressionProcessor.process(dataForAttr, state, outputStreamWriter);
                Collection objects = (Collection) collection;
                state.setForDataIterator(objects.iterator());
                state.setForVarName(varName);
                //changed dom
                //element.removeAttr("data-" + dataForKey);
                for (int i = 0; i < objects.size() - 1; i++) {
                    element.after(element.outerHtml());
                }
            } else {
                element.removeAttr(keyName);
            }
            state.getEngine().put(state.getForVarName(), state.getForDataIterator().next());
            System.out.println("data for: " + varName + " - -" + dataForAttr);
        }
    }
        }



