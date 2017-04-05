package biz.netcentric.processors;

import org.jsoup.nodes.Element;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class JavascriptElementProcessor implements  SlightlyElementProcessor {

    @Override
    public void process(Element element, OutputStreamWriter os) {

        try {
            os.write("Evaluating: " + JavascriptElementProcessor.class + " element: " + element.tagName());
            os.write("</br>");

        } catch (IOException e) {
            e.printStackTrace();
        }
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
    }
}
