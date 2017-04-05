package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter os) throws IOException {

        if (isServerSideScriptTag(element)) {
            state.setIsScript(true);
            return;
        }

        String dataIfAttr = element.attr("data-if");
        if (!dataIfAttr.isEmpty()) {
            ExpressionProcessor expressionProcessor = new ExpressionProcessor();
            Object obj = expressionProcessor.process(dataIfAttr, state, os);
            if (obj != null) {
                state.setShouldRender(true);
                os.write("Evaluated data if should render \n");

                //process other attributes containing el
            } else {
                state.setShouldRender(false);
                os.write("Evaluated data if should not render \n");
                return;
            }
        }

        Attributes elemAttributes = element.attributes();
        for (Attribute attribute : elemAttributes) {
            if (attribute.getKey().startsWith("data-for")) {
                state.setShouldRender(false);
                DataForElementProcessor.process(element, state, os);
                return;

            }
        }
        SimpleElementProcessor.process(element, state, os);
        os.write("\n");

    }



    private static boolean isServerSideScriptTag(Element element) {
        return "script".equals(element.tagName()) && "server/javascript".equals(element.attr("type"));

    }
}

