package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;
import org.jsoup.nodes.TextNode;

/**
 * Created by alinanicorescu on 05/04/2017.
 * Process a text node
 */
public class TextNodeProcessor {

    /**
     * Process a text node, exclude blanks
     * @throws SlightlyProcessingException
     */
    public static void process(TextNode node, SlightlyProcessingContext context) throws SlightlyProcessingException {
        if (node.isBlank()) {
            return;
        }
        TextProcessor.process(node.text(), context);
    }

}


