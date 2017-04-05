package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class TextNodeProcessor {

    public static void process(TextNode node, ProcessingState state, OutputStreamWriter outputStreamWriter) throws IOException {
        if (node.isBlank()) {
            return;
        }
        TextProcessor.process(node.text(), state, outputStreamWriter);

        }

    }


