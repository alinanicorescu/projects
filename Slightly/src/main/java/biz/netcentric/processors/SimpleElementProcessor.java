package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.*;
import org.jsoup.parser.Tag;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SimpleElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter outputStreamWriter) throws IOException, ScriptException {


        StringJoiner startTagHtml = new StringJoiner("");
        startTagHtml.add("<").add(element.tagName());
                if (element.attributes().size() > 0) {
                    startTagHtml.add(element.attributes().html());
                }

        startTagHtml.add(">");
       if (element.childNodes().size() > 0) {
           //in html 5, only non void elements must be closed
          state.setEndTag(new StringJoiner("").add("</").add(element.tagName()).add(">").toString());
        }

        TextProcessor textProcessor = new TextProcessor();
        textProcessor.process(startTagHtml.toString(), state, outputStreamWriter);

    }
}
