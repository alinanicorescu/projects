package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class TextProcessor {

    public static void process(String text, ProcessingState processingState, OutputStreamWriter out) throws IOException {
        out.write("Text processor: " + text + "\n");
    }
}
