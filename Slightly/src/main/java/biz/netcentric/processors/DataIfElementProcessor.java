package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Element;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by alinanicorescu on 07/04/2017.
 */
public class DataIfElementProcessor {

    public static boolean process(Element element, ProcessingState state) throws IOException, ScriptException {


        Map<String, String> html5DataAttr = element.dataset();
        if (html5DataAttr != null) {
            //process data- elements
            String dataIfAttr = html5DataAttr.get("if");
            if (dataIfAttr != null) {
                ExpressionProcessor expressionProcessor = new ExpressionProcessor();
                Object obj = expressionProcessor.process(dataIfAttr, state, null);
                boolean cond = (obj != null) ? (obj instanceof Boolean ? (Boolean) obj : true) : false;

                if (cond) {
                    state.setShouldRender(true);
                    element.removeAttr("data-if");
                } else {
                    state.setShouldRender(false);
                }
                return cond;

            }
        }
        return true;

    }
}