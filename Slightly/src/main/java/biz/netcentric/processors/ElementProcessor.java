package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;
import biz.netcentric.Utils;
import org.jsoup.nodes.Element;


/**
 * Created by alinanicorescu on 05/04/2017.
 * Processor for element nodes
 */
public class ElementProcessor {


    public static void process(Element element, SlightlyProcessingContext context) throws SlightlyProcessingException {
        if (Utils.isServerSideScriptTag(element)) {
            context.setScriptNode(true);
            return;
        }
        //(Data-if element attribute takes precedence over data-for element attribute)
        if (DataIfElementProcessor.process(element, context)) {
            if (DataForElementProcessor.process(element, context)) {
                SimpleElementProcessor.process(element, context);
            }
        }
    }
}

