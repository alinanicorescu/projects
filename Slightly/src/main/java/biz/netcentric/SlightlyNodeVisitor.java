package biz.netcentric;

import biz.netcentric.processors.*;
import org.jsoup.nodes.*;
import org.jsoup.select.NodeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alinanicorescu on 05/04/2017.
 * Visitor class for traversing all html document nodes
 */
public class SlightlyNodeVisitor implements NodeVisitor {

    private SlightlyProcessingContext context;

    private Logger LOGGER = LoggerFactory.getLogger(SlightlyNodeVisitor.class);

    public  SlightlyNodeVisitor(SlightlyProcessingContext context) {
        this.context = context;
    }

    @Override
    public void head(Node node, int i) {

        try {
            context.setCurrentNodeLevel(i);

            if (!context.shouldProcessNode()) {
                return;
            }

            if (node instanceof Document) {
                //add html5 doctype
                context.write("<!DOCTYPE html>");
                return;
            }

            if (node instanceof TextNode) {
                TextNodeProcessor.process((TextNode) node, context);
                return;
            } else if (node instanceof DataNode) {
                DataNodeProcessor.process((DataNode) node, context);
                return;
            }
            if (node instanceof Element) {
                ElementProcessor.process((Element) node, context);
                return;
            }
        } catch (SlightlyProcessingException e) {
                String message = Utils.getExceptionMessage(node);
                LOGGER.error(message, e);
                throw new RuntimeException(message, e);
        }
    }

    @Override
    public void tail(Node node, int i) {

        context.setCurrentNodeLevel(i);
        if (node instanceof Element) {
            try {
                context.writeEndTag();
            } catch (SlightlyProcessingException e) {
                String message = Utils.getExceptionMessage(node);
                LOGGER.error(message, e);
                throw new RuntimeException(message, e);
            }
        }
        context.reset();
    }
}
