package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Element;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class DataForElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter outputStreamWriter) throws IOException, ScriptException {

        String dataForAttr = null;
        String varName = null;

        Map<String, String> html5DataAttr = element.dataset();
        for (String key : html5DataAttr.keySet()) {
            if (key.startsWith("for")) {
                varName = key.substring("for-".length());
                dataForAttr = html5DataAttr.get(key);


                ExpressionProcessor expressionProcessor = new ExpressionProcessor();
                Object collection = expressionProcessor.process(dataForAttr, state, outputStreamWriter);
                System.out.println("data for: " + varName + " - -" + dataForAttr);

            }
        }
        outputStreamWriter.write("Process Data for " + element.tagName());
        outputStreamWriter.write("\n");
    }
}
