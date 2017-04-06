package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SimpleElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter outputStreamWriter) throws IOException, ScriptException {

        String html = element.outerHtml();
        int lastIndex;
        if (element.children().size() > 0) {
            lastIndex = html.indexOf(">");
            state.setEndTag("</" + element.tagName() + ">");
        } else {
            lastIndex = html.indexOf("/>");
        }

        String startTagHtml = html.substring(0, lastIndex + 1);

        TextProcessor textProcessor = new TextProcessor();
        textProcessor.process(startTagHtml, state, outputStreamWriter);

        outputStreamWriter.write("Simple Element Processor " + element.tagName());
        outputStreamWriter.write("\n");
    }
}
