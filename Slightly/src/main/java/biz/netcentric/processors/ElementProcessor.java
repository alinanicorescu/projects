package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter os) throws IOException, ScriptException {

        if (isServerSideScriptTag(element)) {
            state.setIsScript(true);
            return;
        }

        Map<String, String> html5DataAttr = element.dataset();
        if (html5DataAttr != null) {
            //process data- elements
            String dataIfAttr = html5DataAttr.get("if");
            if (dataIfAttr != null) {
                ExpressionProcessor expressionProcessor = new ExpressionProcessor();
                Object obj = expressionProcessor.process(dataIfAttr, state, os);
                if (obj != null) {
                    state.setShouldRender(true);
                } else {
                    state.setShouldRender(false);
                    return;
                }
            }

            for (String key : html5DataAttr.keySet()) {
                if (key.startsWith("for")) {
                    state.setShouldRender(false);
                    DataForElementProcessor.process(element, state, os);
                    return;

                }
            }
        }
        SimpleElementProcessor.process(element, state, os);

    }



    private static boolean isServerSideScriptTag(Element element) {
        return "script".equals(element.tagName()) && "server/javascript".equals(element.attr("type"));

    }
}

