package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SimpleElementProcessor {

    public static void process(Element element, ProcessingState state, OutputStreamWriter outputStreamWriter) throws IOException {
        outputStreamWriter.write("Simple Element Processor " + element.tagName());
        outputStreamWriter.write("\n");
    }
}
